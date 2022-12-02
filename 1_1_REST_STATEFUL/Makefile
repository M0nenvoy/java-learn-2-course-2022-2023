ROUTE  = localhost:8080/songs
FLAGAS = -i

all:
	mvn spring-boot:run

get:
	curl $(ROUTE) $(FLAGS)
getid:
	curl $(ROUTE)/1 $(FLAGS)
delete:
	curl -XDELETE $(ROUTE)/1 $(FLAGS)
post:
	curl $(ROUTE) $(FLAGS) -XPOST \
	-H "content-type: application/json" \
	-d '{"name": "Sample name", "artistName": "Sample artist", "auditions": 0}'

post-name-empty:
	curl $(ROUTE) $(FLAGS) -XPOST \
	-H "content-type: application/json" \
	-d '{"name": "", "artistName": "", "auditions": 0}'

post-auditions-null:
	curl $(ROUTE) $(FLAGS) -XPOST \
	-H "content-type: application/json" \
	-d '{"name": "Sample name", "artistName": "Sample artist", "auditions": null}'

update:
	curl $(ROUTE)/1 $(FLAGS) -XPUT \
	-H "content-type: application/json" \
	-d '{"name": "Nirvana", "artistName": "Smells Like Teen Spirit", "auditions": 100}'
listen:
	curl $(ROUTE)/listen $(FLAGS) -XPOST \
	-H "content-type: application/json" \
	-d '{ "auditions": 2, "songs": [ 1, 2, 3 ] }'
listenid:
	curl $(ROUTE)/1/listen $(FLAGS) -XPOST \
	-H "content-type: application/json" \
	-d '{ "auditions": 2 }'
sort:
	curl $(ROUTE)/listen?limit=3 $(FLAGS)

.PHONY: post update delete get getid listen listenid sort
