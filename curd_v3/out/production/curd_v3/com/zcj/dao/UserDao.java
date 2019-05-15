// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   UserDao.java

package com.zcj.dao;

import com.zcj.domain.User;
import com.zcj.utils.JdbcUtils;
import java.util.List;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserDao
{

	private JdbcTemplate jdbcTemplate;

	public UserDao()
	{
		jdbcTemplate = new JdbcTemplate(JdbcUtils.getDataSource());
	}

	public List queryAll()
	{
		List userList = jdbcTemplate.query("SELECT * FROM tab_user;", new BeanPropertyRowMapper(com/zcj/domain/User));
		return userList;
	}

	public User findById(String id)
	{
		User user = (User)jdbcTemplate.queryForObject("select * from tab_user where id=?", new BeanPropertyRowMapper(com/zcj/domain/User), new Object[] {
			id
		});
		return user;
	}

	public int updateUser(User user)
	{
		Object params[] = {
			user.getName(), user.getSex(), user.getAge(), user.getAddress(), user.getQq(), user.getEmail(), user.getId()
		};
		int count = jdbcTemplate.update("UPDATE tab_user set name =?,sex=?,age=?,address=?,qq=?,email=? where id=?", params);
		return count;
	}

	public int add(User user)
	{
		Object params[] = {
			user.getId(), user.getName(), user.getSex(), user.getAge(), user.getAddress(), user.getQq(), user.getEmail()
		};
		int count = jdbcTemplate.update("insert into tab_user(id,name,sex,age,address,qq,email)values (?,?,?,?,?,?,?)", params);
		return count;
	}

	public int delete(String id)
	{
		int count = jdbcTemplate.update("delete from tab_user where id=?", new Object[] {
			id
		});
		return count;
	}
}
