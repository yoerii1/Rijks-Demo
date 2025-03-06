# Rijks-Demo
Bol.com interview process required an Android project that could be discussed during the technical interview. 
I decided to reuse the assessment I did for Albert Heijn. Below this line is the original readme for that assessment:

Demo project as part of AH hiring process. It consumes the Rijksmuseum API and shows a list of art items as well as a detail screen
with more details about the art items.

## Assignment description
The Assignment
- Use the Rijksmuseum API, see documentation here: https://data.rijksmuseum.nl/object-metadata/api/
- We would like to see an app with at least two screens:
  - An overview page with a list of items:
    - Should be visually split in sections with headers, grouped by author, with the author's
  name in the header.
    - Items should have text and image.
    - Screen should be paginated. 
  - A detail page, with more details about the item.
    - Loading of data and images should be asynchronous, a loading indicator should be shown.
    - Automated tests should be present (full coverage not needed of course).
    - Please use Fragments (used heavily in our code base) with a single Activity, or Compose.
    - Please use Kotlin (100% of our codebase is).
    - The time frame for this assignment is approx 6 hours

## Rijks API
To be able to run the app, the API key for the Rijksmuseum API needs to be added to the local.properties file using the API_KEY identifier.
