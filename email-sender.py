from datetime import datetime
import smtplib
import sys

if len(sys.argv) < 4:
    print("Not enough arguments. Expected at least 3, got " + len(sys.argv) - 1)
    exit(1)

TO = sys.argv[1]
buildOutput = sys.argv[2]
testOutput = sys.argv[3]

# Get the current local time in a specific format
currentTime = datetime.now().strftime("%Y-%m-%d %H:%M:%S")

with open(".credentials") as ifile:
    FROM=ifile.readline().rstrip('\n')
    SMTP_PASS=ifile.readline().rstrip('\n')

SUBJECT = f"Code status {currentTime}"
BODY = f"{buildOutput}\n\n{testOutput}"

SMTP_SERVER = "smtp.gmail.com"
SMTP_PORT = 587
SMTP_USER = FROM

# Compose the email
message = f"From: {FROM}\nTo: {TO}\nSubject: {SUBJECT}\n\n{BODY}"

# Send the email
with smtplib.SMTP(SMTP_SERVER, SMTP_PORT) as server:
    server.starttls()
    server.login(SMTP_USER, SMTP_PASS)
    server.sendmail(FROM, TO, message)

print(f"Email sent to {TO} with subject: {SUBJECT}.")