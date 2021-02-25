import json
import pandas as pd
from sklearn.metrics.pairwise import linear_kernel
from sklearn.feature_extraction.text import TfidfVectorizer

movies = pd.read_csv('Mlseries.csv', encoding='latin-1', usecols=['Title', 'Genre1', 'Keywords'])

movieTitles = pd.read_csv('Mlseries.csv', encoding='latin-1', usecols=['Title'])

# Break up the big genre string into a string array
movies['Keywords'] = movies['Keywords'].str.split('|')
movieTitles['Title'] = movieTitles['Title'].str.split('|')

# Convert genres to string value
movies['Keywords'] = movies['Keywords'].fillna("").astype('str')
movieTitles['Title'] = movieTitles['Title'].fillna("").astype('str')

tf = TfidfVectorizer(analyzer='word', ngram_range=(1, 2), min_df=0, stop_words='english')
tfidf_matrix = tf.fit_transform(movies['Keywords'], movieTitles['Title'])


cosine_sim = linear_kernel(tfidf_matrix, tfidf_matrix)
# cosine_sim[:4, :4]

# Build a 1-dimensional array with movie titles
titles = movies['Title']
indices = pd.Series(movies.index, index=movies['Title'])


# Function that get movie recommendations based on the cosine similarity score of movie genres
def series_recommendations(title):
    try:
        idx = indices[title]
        sim_scores = list(enumerate(cosine_sim[idx]))
        sim_scores = sorted(sim_scores, key=lambda x: x[1], reverse=True)
        sim_scores = sim_scores[1:21]
        movie_indices = [i[0] for i in sim_scores]
        return json.loads(titles.iloc[movie_indices].head(10).to_json())
    except:
        return 'Not Found Series!'
