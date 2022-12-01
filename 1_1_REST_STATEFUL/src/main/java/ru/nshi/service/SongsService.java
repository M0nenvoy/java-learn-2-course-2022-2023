package ru.nshi.service;
import ru.nshi.model.SongModel;
import ru.nshi.model.SongPostModel;

import java.util.List;

import ru.nshi.model.AuditionsModel;

public interface SongsService {
	SongModel[]   getSongs();
	SongModel     createSong(SongPostModel post);
	SongModel     getSongById(int id);
	SongModel[]   getSongsByIds(List<Integer> ids);
	SongModel     deleteSongById(int id);
	SongModel[]   getSortedSongsByAuditions(int limit);
	SongModel     listenSongById(int id, AuditionsModel auditions);
	void          listenSongs(List<Integer> ids, int inc);
	SongModel     updateSongById(int id, SongPostModel post);
}
