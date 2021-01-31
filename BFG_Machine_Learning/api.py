import os
import re
import json
from flask_cors import CORS
from flask import Flask, jsonify, request, url_for
from movie import genre_recommendations

app = Flask(__name__)
CORS(app)


@app.route('/')
def hello_world():
    return '<h1>Ulooo, World!</h1>'


@app.route('/title')
def word_test():
    w = request.args['word']
    try:
        return jsonify(json.loads(genre_recommendations(w).head(30).to_json()))
    except KeyError:
        return jsonify({
            'message': 'Not found movie!!',
            'status': 404
        })


if __name__ == '__main__':
    app.run(debug=True)
