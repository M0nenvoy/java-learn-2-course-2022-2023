package ru.nshi.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ru.nshi.error.SongException;
import ru.nshi.model.AuditionsIdsModel;
import ru.nshi.model.AuditionsModel;
import ru.nshi.model.SongModel;
import ru.nshi.model.SongPostModel;
import ru.nshi.service.SongsService;

@RestController
@RequestMapping(CrudController.MAPPING)
public class CrudController {
	public static final String MAPPING = "/songs";
	@Autowired
	SongsService songsService;

	private boolean validateSong(SongPostModel song)
	{
		return (
			song.getName()       !=  null    &&
			song.getArtistName() !=  null    &&
			song.getAuditions()  >  -1       &&
			!song.getArtistName().isBlank()  &&
			!song.getName().isBlank()
		);
	}

	private void assertValid(SongPostModel song)
	{
		if (validateSong(song)) {
			return;
		}
		throw new SongException("SongPostModel is invalid", HttpStatus.BAD_REQUEST);
	}

	@GetMapping
	public SongModel[] getSongs()
	{
		return songsService.getSongs();
	}

	@PostMapping
	public SongModel createSong(@RequestBody SongPostModel post)
	{
		assertValid(post);
		SongModel song = songsService.createSong(post);
		return song;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public SongModel getSongById(@PathVariable int id)
	{
		SongModel song = songsService.getSongById(id);
		return song;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public SongModel updateSongById(@PathVariable int id,
	                                @RequestBody  SongPostModel post)
	{
		assertValid(post);
		SongModel song = songsService.updateSongById(id, post);
		return song;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public SongModel deleteSongById(@PathVariable int id)
	{
		SongModel song = songsService.deleteSongById(id);
		return song;
	}

	@RequestMapping(value = "/listen", method=RequestMethod.GET)
	public SongModel[] getSortedSongsByAuditions(@RequestParam(defaultValue = "5") int limit)
	{
		if (limit < 1) {
			throw new SongException(
				String.format("Limit less than 1 is invalid (%d)", limit),
				HttpStatus.BAD_REQUEST
			);
		}

		SongModel[] sorted = songsService.getSortedSongsByAuditions(limit);
		return sorted;
	}

	@RequestMapping(value = "/listen", method=RequestMethod.POST)
	public SongModel[] listenSongs(@RequestBody  AuditionsIdsModel auditions)
	{
		final int[] ids = auditions.getSongs();
		final int     n = auditions.getAuditions();

		List<Integer> idList = Arrays.stream(ids)
			.mapToObj(Integer::valueOf)
			.collect(Collectors.toList());

		songsService.listenSongs(idList, n);
		return songsService.getSongsByIds(idList);
	}

	@RequestMapping(value = "/{id}/listen", method=RequestMethod.POST)
	public SongModel listenSongById(@PathVariable int id,
	                                @RequestBody  AuditionsModel auditions)
	{
		SongModel song = songsService.listenSongById(id, auditions);
		return song;
	}

	@ExceptionHandler({SongException.class})
	public ResponseEntity<?> handleError(SongException ex)
	{
		return new ResponseEntity<>(
			ex.getError(),
			ex.getStatus()
		);
	}
}
