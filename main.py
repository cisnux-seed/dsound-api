import base64
import hashlib
import hmac
import os
import time
import requests
from flask import Flask, request, jsonify

app = Flask(__name__)

# Path where the uploaded files will be saved
UPLOAD_FOLDER = 'uploads'
os.makedirs(UPLOAD_FOLDER, exist_ok=True)

@app.route('/upload', methods=['POST'])
def upload_file():
    if 'file' not in request.files:
        return jsonify({"error": "No file part in the request"}), 400

    file = request.files['file']
    
    if file.filename == '':
        return jsonify({"error": "No selected file"}), 400
    
    if file:
        file_path = os.path.join(UPLOAD_FOLDER, file.filename)
        file.save(file_path)
        
        # Call the ACR service with the uploaded file
        response = call_acr_service(file_path)
        
        return jsonify(
            response
        ), 200

def call_acr_service(file_path):
    access_key = "c70f7b38afcd75fb44ab66056b8bf9ce"
    access_secret = "t1dS0ACBgcgCeA8b6jgx8tApATIr9ZaPXBnK2QOQ"
    requrl = "https://identify-ap-southeast-1.acrcloud.com/v1/identify"
    
    http_method = "POST"
    http_uri = "/v1/identify"
    data_type = "audio"
    signature_version = "1"
    timestamp = time.time()
    
    string_to_sign = http_method + "\n" + http_uri + "\n" + access_key + "\n" + data_type + "\n" + signature_version + "\n" + str(timestamp)
    
    sign = base64.b64encode(hmac.new(access_secret.encode('ascii'), string_to_sign.encode('ascii'), digestmod=hashlib.sha1).digest()).decode('ascii')
    
    files = {
        'sample': (os.path.basename(file_path), open(file_path, 'rb'), 'audio/3gp')
    }
    
    data = {
        'access_key': access_key,
        'sample_bytes': os.path.getsize(file_path),
        'timestamp': str(timestamp),
        'signature': sign,
        'data_type': data_type,
        "signature_version": signature_version
    }
    
    response = requests.post(requrl, files=files, data=data)

    print(response.text)
    
    return response.json()

if __name__ == '__main__':
    app.run(port=9090, debug=True)