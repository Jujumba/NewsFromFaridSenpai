"""
A simple script to test api
"""
import requests

url = 'http://localhost:8080/api/v1'
amount = 3

request = requests.post(url, headers={'Authorization': 'vIHYmlEk4yXQEXlA03D9UmT7M'}, json={'amount': amount})
print(request.text)