package ru.nshi.model;

public class AuditionsIdsModel {
	private int   auditions;
	private int[] songs;

	public AuditionsIdsModel(int auditions, int[] songs) {
		this.auditions = auditions;
		this.songs     = songs;
	}
	public AuditionsIdsModel() { }

	public int getAuditions() {
		return auditions;
	}
	public int[] getSongs() {
		return songs;
	}
}
