package ru.nshi.service;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ru.nshi.error.SongException;
import ru.nshi.model.AuditionsModel;
import ru.nshi.model.SongModel;
import ru.nshi.model.SongPostModel;

@Service
public class SongsServiceImpl implements SongsService {
	/*
	 *  Wait. A hash map that uses integers as keys? That's literally an array.
	 *  I guess I'll leave it as a hashmap for... ugh... clarity
	 */
	private ConcurrentHashMap<Integer, SongModel> songsMap = new ConcurrentHashMap<>();
	private AtomicInteger                         autoId   = new AtomicInteger(0);

	private boolean validateSong(SongPostModel song)
	{
		return (
			song.getName()       !=  null &&
			song.getArtistName() !=  null &&
			song.getAuditions()   >  -1
		);
	}

	private void assertValid(SongPostModel song)
	{
		if (validateSong(song)) {
			return;
		}
		throw new SongException("SongPostModel is invalid", HttpStatus.BAD_REQUEST);
	}

	@Override
	public SongModel[] getSongs()
	{
		Collection<SongModel> c = songsMap.values();
		return c.toArray(new SongModel[c.size()]);
	}

	@Override
	public SongModel createSong(SongPostModel post)
	{
		assertValid(post);
		int id           = autoId.incrementAndGet();
		SongModel retval = new SongModel(id, post);
		songsMap.put(id, retval);
		
		return retval;
	}

	@Override
	public SongModel getSongById(int id) throws SongException
	{
		SongModel retval = songsMap.getOrDefault(id, null);
		if (retval != null)
			return retval;

		throw new SongException(
			String.format("Song with id (%d) doesn't exist", id),
			HttpStatus.NOT_FOUND
		);
	}

	@Override
	public SongModel deleteSongById(int id) throws SongException
	{
		SongModel retval = getSongById(id);
		songsMap.remove(id);
		return retval;
	}

	@Override
	public SongModel[] getSortedSongsByAuditions(int limit)
	{
		/*
		*  В задании не указано, что необходимо сортировать
		*  при помощи сортировщика, написанного в первом задании.
		*/
		return songsMap.values()
			.stream()
			.sorted(Comparator.comparingInt(SongModel::getAuditions))
			.limit(limit)
			.toArray(SongModel[]::new);
	}

	@Override
	public void listenSongs(List<Integer> ids, int n)
	{
		songsMap.values()
			.stream()
			.filter((SongModel v) -> ids.contains(v.getId()))
			.forEach((SongModel v) -> v.addAuditions(n));
	}

	@Override
	public SongModel listenSongById(int id, AuditionsModel auditions) throws SongException
	{
		SongModel retval = getSongById(id);
		retval.addAuditions(auditions.getAuditions());
		return retval;
	}

	@Override
	public SongModel[] getSongsByIds(List<Integer> ids)
	{
		SongModel[] songs = ids.stream()
			.map(this::getSongById)
			.toArray(SongModel[]::new);

		return songs;
	}

	@Override
	public SongModel updateSongById(int id, SongPostModel post)
	{
		getSongById(id); // Убедимся в существовании песни с таким id
		assertValid(post);

		SongModel update = new SongModel(id, post);
		songsMap.put(id, update);
		return update;
	}
}
