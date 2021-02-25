from flask import Flask, jsonify, request
from flask_cors import CORS

from generic_recommendation import GenericRecommendations


series_rec = GenericRecommendations('Mlseries.csv')
movies_rec = GenericRecommendations('Mlmovies.csv')

app = Flask(__name__)
CORS(app)


@app.route('/')
def hello_world():
    return '<h1>Ulooo, World!</h1>'


@app.route('/movies')
def movies_test():
    w = request.args['word']
    try:
        return jsonify(movies_rec.recommendations((w, "Not Found Movies!")))
    except Exception as error:
        return jsonify({
            'message': str(error),
            'status': 503
        })


@app.route('/series')
def series_test():
    w = request.args['word']
    try:
        return jsonify(series_rec.recommendations((w, "Not Found Series!")))
    except Exception as error:
        return jsonify({
            'message': str(error),
            'status': 503
        })


@app.route('/all')
def all_test():
    w = request.args['word']

    try:
        return jsonify({
                'Series': series_rec.recommendations(w, "Not Found Series!"),
                'Movies': movies_rec.recommendations(w, "Not Fount Movies!")
        })
    except Exception as error:
        return jsonify({
            'message': str(error),
            'status': 503
        })


if __name__ == '__main__':
    app.run(debug=True)
