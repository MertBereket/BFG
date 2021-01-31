import pandas as pd
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import linear_kernel

movies = pd.read_csv('Mlmovies.csv', encoding='latin-1',
                     usecols=['Tmdbid', 'Title', 'Genre1', 'Genre2', 'Genre3', 'Genre4', 'Genre5', 'Keywords'])

# Break up the big genre string into a string array
movies['Genre1'] = movies['Genre1'].str.split('|')
movies['Keywords'] = movies['Keywords'].str.split('|')
# Convert genres to string value
movies['Genre1'] = movies['Genre1'].fillna("").astype('str')
movies['Keywords'] = movies['Keywords'].fillna("").astype('str')

tf = TfidfVectorizer(analyzer='word', ngram_range=(1, 2), min_df=0, stop_words='english')
tfidf_matrix = tf.fit_transform(movies['Genre1'])
tfidf_matrix2 = tf.transform(movies['Keywords'])

cosine_sim = linear_kernel(tfidf_matrix2, tfidf_matrix2)
# cosine_sim[:4, :4]

# Build a 1-dimensional array with movie titles
titles = movies['Title']
indices = pd.Series(movies.index, index=movies['Title'])


# Function that get movie recommendations based on the cosine similarity score of movie genres
def genre_recommendations(title):
    idx = indices[title]
    sim_scores = list(enumerate(cosine_sim[idx]))
    sim_scores = sorted(sim_scores, key=lambda x: x[1], reverse=True)
    sim_scores = sim_scores[0:8021]
    movie_indices = [i[0] for i in sim_scores]
    return titles.iloc[movie_indices]
