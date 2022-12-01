package ru.nshi.model;

public class SongModel extends SongPostModel {
	private int id;

	public SongModel(int id, int auditions, String artistName, String name) {
		super(auditions, artistName, name);
		this.id = id;
	}

	public SongModel(int id, SongPostModel post) {
		super(post.getAuditions(), post.getArtistName(), post.getName());
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
