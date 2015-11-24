# Mongo Twitter
Dibuat oleh:

- Fahziar Riesad Wutono - 13512012
- Luthfi Hamid Masykuri - 13512100

## Struktur Folder

- /src: Direktori source code
- /bin: File executable

## Petunjuk Penggunaan
1. Jalankan program: buka folder bin dan ketikkan perintah `java -jar MongoTwitter.jar`
2. Lakukan registrasi atau login
3. Twitter siap digunkan. Gunakan perintah yang ditampilkan layar untuk menggunakan Mongo Twitter.

## Daftar Perintah
1. `/follow <username>`: follow a user
2. `/tweet <tweet>`: post a tweet
3. `/getTweet <username>`: view all tweets from an user user
4. `/timeline <username>`: view timeline from an user
5. `/exit` : quit application

## Petunjuk Kompilasi
Program ini dibuat dengan menggunakan IDE IntelliJ. Bukalah project dengan menggunakan ItelliJ dan jalankan perintah `Build`.

## Database
Program ini menggunakan database `luthfihm`

## Daftar Query
### Mendaftar user baru
`db.user.insertOne({
	"username" : <username>,
    "password" : <password>
})`

### Follow a friend
`db.followers.insertOne({
	"username" : <friend>,
    "follower" : <follower>,
    "since": <since>
})`

`db.friends.insertOne({
	"username" : <follower>,
    "follower" : <friend>,
    "since": <since>
})`

### Post tweet
`db.tweets.insertOne({
	"tweet_id" : <tweet_id>,
    "username" : <username>,
    "body" : <body>
})`

`db.timeline.insertOne({
	"username" : <username>,
    "time" : <time>,
    "tweet_id" : <tweet_id>
})`

`db.userline.insertOne({
	"username" : <username>,
    "time" : <time>,
    "tweet_id" : <tweet_id>
})`

### Menampilkan tweet per user
`db.tweets.find({"username" : <username>})`

### Menampilkan timeline per user
`db.timeline.find({"username" : <username>})`

