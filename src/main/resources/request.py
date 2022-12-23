"""
A simple script to test api
"""
import requests

url = 'http://localhost:8080/api/v1'
amount = 3
api_key = None #todo: paste your API-key generated in your profile (localhost:8080/profile)
request = requests.post(url, headers={'Authorization': api_key}, json={'amount': amount})
print(request.text)