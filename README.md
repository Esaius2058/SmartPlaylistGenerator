# EchoSeed (formerly SmartPlaylistGenerator)

🚨 **Update**: This project has been migrated from Java to Python and renamed **EchoSeed**.

## 📦 Why the Migration?
As of November 2024, Spotify deprecated access to key Web API features for new apps — including:

- `audio-features` (e.g., danceability, valence, tempo)
- `recommendations`
- `related-artists`

These features were essential for the original Java-based SmartPlaylistGenerator, which depended on real-time Spotify API access for generating vibe-based playlists.

Rather than scrap the vision, we’ve rebuilt the project in Python to:

✅ Use prebuilt Spotify datasets (e.g., from Kaggle, AICrowd)
✅ Run offline k-means clustering on track features
✅ Use GPT and AI tools to tag moods and generate recommendations
✅ Leverage Python’s rich ecosystem for data science, ML, and AI

## 📁 New Home: [`Echo-Seed`](https://github.com/your-username/Echo-Seed)
The current version of the project — built entirely in Python — is maintained in the new `Echo-Seed` repository.

The Java version has been archived for reference.

## 🧠 Technologies (Python version)
- `pandas`, `scikit-learn` for data clustering
- `spotipy` for OAuth and Spotify data access (where applicable)
- `openai` or similar for AI-based tagging
- `Flask` for OAuth callback
- `matplotlib` or `seaborn` for cluster visualization

## 📌 What’s Next
- Console UI for selecting playlists or mood clusters
- GPT-driven mood detection
- Optionally pushing back to Spotify as custom playlists (via `spotipy`)

---
For details on the Python implementation, go to: [`Echo-Seed`](https://github.com/your-username/Echo-Seed)

