import json
import pandas as pd
from sklearn.metrics.pairwise import linear_kernel
from sklearn.feature_extraction.text import TfidfVectorizer


class GenericRecommendations:
    def __init__(self, csv_file):
        self.dataset = pd.read_csv(csv_file, encoding='latin-1', usecols=['Title', 'Genre1', 'Keywords'])
        self.dataset_title = pd.read_csv(csv_file, encoding='latin-1', usecols=['Title'])

        self.dataset['Keywords'] = self.dataset['Keywords'].str.split('|')
        self.dataset_title['Title'] = self.dataset_title['Title'].str.split('|')

        self.dataset['Keywords'] = self.dataset['Keywords'].fillna("").astype('str')
        self.dataset_title['Title'] = self.dataset_title['Title'].fillna("").astype('str')

        self.tf = TfidfVectorizer(analyzer='word', ngram_range=(1, 2), min_df=0, stop_words='english')
        self.tfidf_matrix = self.tf.fit_transform(self.dataset['Keywords'], self.dataset_title['Title'])

        self.cosine_sim = linear_kernel(self.tfidf_matrix, self.tfidf_matrix)

        self.titles = self.dataset['Title']
        self.indices = pd.Series(self.dataset.index, index=self.dataset['Title'])

    def recommendations(self, title, error_msg="Not Found!"):
        try:
            idx = self.indices[title]
            sim_scores = list(enumerate(self.cosine_sim[idx]))
            sim_scores = sorted(sim_scores, key=lambda x: x[1], reverse=True)
            sim_scores = sim_scores[1:21]
            movie_indices = [i[0] for i in sim_scores]
            return json.loads(self.titles.iloc[movie_indices].head(10).to_json())
        except:
            return error_msg
