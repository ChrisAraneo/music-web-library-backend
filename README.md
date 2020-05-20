# Music Web Library Backend

Wykonawcy

GET /api/artists
GET /api/artists/{ID}
POST /api/artists?type={typeID}
PUT /api/artists/{ID}
DELETE /api/artists/{ID}

Rodzaje działaności muzycznej

GET /api/artisttypes
GET /api/artisttypes/{ID}
POST /api/artisttypes
PUT /api/artisttypes/{ID}
DELETE /api/artisttypes/{ID}

URL wykonawców

GET /api/artistulrs
GET /api/artistulrs/{ID}
POST /api/artisturls?artist={ArtistID}
PUT /api/artisturls/{ID}
DELETE /api/artisturls/{ID}

Piosenki

GET /api/songs
GET /api/songs/{ID}
POST /api/songs
PUT /api/songs/{ID}
DELETE /api/songs/{ID}

URL piosenek

GET /api/songurls
GET /api/songurls/{ID}
POST /api/songurls?song={songID}
PUT /api/songurls/{ID}
DELETE /api/songurld/{ID}

Użytkownicy (TODO)

GET /api/users
GET /api/users/{ID}
POST /api/users
PUT /api/users/{ID}
DELETE /api/users/{ID}

Recenzja albumu

GET /api/review
GET /api/review/{ID}
POST /api/review?album={albumID}&user={userID}
PUT /api/review/{ID}
DELETE /api/review/{ID}

