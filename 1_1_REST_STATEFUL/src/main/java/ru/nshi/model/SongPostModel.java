package ru.nshi.model;

import org.springframework.http.HttpStatus;

import ru.nshi.error.SongException;

public class SongPostModel {
	private int    auditions;
	private String artistName;
	private String name;

	public SongPostModel() { }
	public SongPostModel(int auditions, String artistName, String name)
	{
		this.auditions  = auditions;
		this.artistName = artistName;
		this.name       = name;
	}

	public int getAuditions()
	{
		return auditions;
	}

	public String getArtistName()
	{
		return artistName;
	}

	public String getName()
	{
		return name;
	}

	public void addAuditions(int auditions)
	{
		if (auditions > 0) {
			this.auditions += auditions;
			return;
		}

		throw new SongException(
			String.format("Number of auditions less than 1 is invalid", auditions),
			HttpStatus.BAD_REQUEST
		);
	}
}
