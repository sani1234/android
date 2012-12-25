package com.yodi.bodyslap;

/**
 * Handler for accessing and writing into SQLite Database
 * @author yodi
 *
 */
public class UserDatabase {
	private long id;
	private Integer day;
	private Integer isExercise = 0;
	
	/**
	 * Get ID of table records
	 * @return long
	 */
	public long getId() {
		return id;
	}
	
	/**
	 * Set ID of table trackers
	 * @param id
	 */
	public void setId(long id) {
		this.id = id;
	}
	
	/**
	 * Get the day valu
	 * @return Integer
	 */
	public Integer getDay() {
		return day;
	}
	
	/**
	 * Set day fitness
	 * @param day
	 */
	public void setDay(Integer day) {
		this.day = day;
	}
	
	/**
	 * Get is exercise
	 */
	public Integer getIsExercise() {
		return isExercise;
	}
	
	/**
	 * Set is_exercise
	 */
	public void setIsExercise(Integer isExercise) {
		this.isExercise = isExercise;
	}
	
}	
