package com.example.sqlliteproject.models.Tables;

import java.util.Date;

public class ToDoList {
private  long taskId;
private String taskName;
private String taskDescription;
private int DurationTime;
private String taskDate;
private boolean status;
private long user_id;
private String user_email;


 public ToDoList(long taskId, String taskName, String taskDescription, int durationTime, String taskDate,long user_id,String user_email) {
  this.taskId = taskId;
  this.taskName = taskName;
  this.taskDescription = taskDescription;
  DurationTime = durationTime;
  this.taskDate = taskDate;
  this.user_id = user_id;
  this.user_email = user_email;
 }

 public ToDoList(String taskName, String taskDescription, int durationTime, String taskDate,long user_id,String user_email) {
  this.taskName = taskName;
  this.taskDescription = taskDescription;
  DurationTime = durationTime;
  this.taskDate = taskDate;
  this.user_id = user_id;
  this.user_email = user_email;
 }
 public ToDoList(String taskName, String taskDescription, int durationTime,long user_id,String user_email) {
  this.taskName = taskName;
  this.taskDescription = taskDescription;
  DurationTime = durationTime;
  this.taskDate = taskDate;
  this.user_id = user_id;
  this.user_email = user_email;
 }

 public long getTaskId() {
  return taskId;
 }

 public void setTaskId(long taskId) {
  this.taskId = taskId;
 }

 public String getTaskName() {
  return taskName;
 }

 public void setTaskName(String taskName) {
  this.taskName = taskName;
 }

 public String getTaskDescription() {
  return taskDescription;
 }

 public void setTaskDescription(String taskDescription) {
  this.taskDescription = taskDescription;
 }

 public int getDurationTime() {
  return DurationTime;
 }

 public void setDurationTime(int durationTime) {
  DurationTime = durationTime;
 }

 public String getTaskDate() {
  return taskDate;
 }

 public void setTaskDate(String taskData) {
  this.taskDate = taskData;
 }

 public long getUser_id() {
  return user_id;
 }

 public void setUser_id(long user_id) {
  this.user_id = user_id;
 }

 public String getUser_email() {
  return user_email;
 }

 public void setUser_email(String user_email) {
  this.user_email = user_email;
 }

 public boolean isStatus() {
  return status;
 }

 public void setStatus(boolean status) {
  this.status = status;
 }
}
