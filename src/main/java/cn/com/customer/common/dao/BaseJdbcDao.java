package cn.com.customer.common.dao;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.AbstractLobCreatingPreparedStatementCallback;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobCreator;
import org.springframework.jdbc.support.lob.LobHandler;

public class BaseJdbcDao extends JdbcDaoSupport{

	private static Logger log = Logger.getLogger(BaseJdbcDao.class);
	
	@Resource
	JdbcTemplate jdbcTemplate;
//	public void setMyJdbcTemplate(@Qualifier("jdbcTemplate") JdbcTemplate jdbcTemplate) {
//		super.setJdbcTemplate(jdbcTemplate);
//	}
	
	/**
	 * Int查询
	 */
	public int queryForInt(String sql) {
		log.debug(sql);
		return this.getJdbcTemplate().queryForInt(sql);
	}
	/**
	 * Int查询(预编译)
	 */
	public int queryForInt(String sql,Object[] args) {
		log.debug(sql);
		return this.getJdbcTemplate().queryForInt(sql, args);
	}

	/**
	 * Long查询
	 */
	public Long queryForLong(String sql) {
		log.debug(sql);
		return this.getJdbcTemplate().queryForLong(sql);
	}
	
	/**
	 * Long查询(预编译)
	 */
	public Long queryForLong(String sql,Object[] args) {
		log.debug(sql);
		return this.getJdbcTemplate().queryForLong(sql, args);
	}

	/**
	 * map查询
	 */
	public Map<String, Object> queryForMap(String sql) {
		List<Map<String, Object>> list = queryForList(sql);
		Map<String, Object> resultMap = null;
		for (Map<String, Object> map : list) {
			resultMap = map;
			break;
		}
		return resultMap;
	}
	
	/**
	 * map查询(预编译)
	 */
	public Map<String, Object> queryForMap(String sql,Object[] args) {
		List<Map<String, Object>> list = queryForList(sql,args);
		Map<String, Object> resultMap = null;
		for (Map<String, Object> map : list) {
			resultMap = map;
			break;
		}
		return resultMap;
	}

	/**
	 * List查询
	 */
	public List<Map<String, Object>> queryForList(String sql) {
		log.debug(sql);
		List<Map<String, Object>> list = this.getJdbcTemplate().queryForList(sql);
		return list;
	}
	
	/**
	 * List查询(预编译)
	 */
	public List<Map<String, Object>> queryForList(String sql,Object[] args) {
		log.debug(sql);
		List<Map<String, Object>> list = this.getJdbcTemplate().queryForList(sql, args);
		return list;
	}
	
	/**
	 * 添加、修改、删除
	 */
	public int update(String sql) {
		log.debug(sql);
		return this.getJdbcTemplate().update(sql);
	}

	/**
	 * 添加、修改、删除(预编译)
	 */
	public int update(String sql, Object[] obj) {
		log.debug(sql);
		return this.getJdbcTemplate().update(sql, obj);
	}

	/**
	 * 添加、修改、删除和DDL
	 */
	public void execute(String sql) {
		log.debug(sql);
		this.getJdbcTemplate().execute(sql);
	}

	/**
	 * 保存Clob
	 */
	public boolean updateForClob(String sql, final byte[] b) {
		log.debug(sql);
		boolean result = true;
		try {
			final ByteArrayInputStream bis = new ByteArrayInputStream(b);
			final InputStreamReader clobReader = new InputStreamReader(bis);
			final LobHandler lobHandler = new DefaultLobHandler();
			this.getJdbcTemplate().execute(sql, new AbstractLobCreatingPreparedStatementCallback(lobHandler) {
				protected void setValues(PreparedStatement ps, LobCreator lobCreator) {
					try {
						lobCreator.setClobAsCharacterStream(ps, 1, clobReader, (int) b.length);
					} catch (SQLException e) {
						throw new RuntimeException(e.getMessage());
					}
				}
			});
			if (null != clobReader) {
				clobReader.close();
			}
			if (null != bis) {
				bis.close();
			}
		} catch (Exception e) {
			result = false;
			throw new RuntimeException(e.getMessage());
		}
		return result;
	}

	/**
	 * 保存Blob
	 */
	public boolean updateForBlob(String sql, final File file, final InputStream inputStream) {
		log.debug(sql);
		boolean result = true;
		try {
			final LobHandler lobHandler = new DefaultLobHandler();
			this.getJdbcTemplate().execute(sql, new AbstractLobCreatingPreparedStatementCallback(lobHandler) {
				protected void setValues(PreparedStatement ps, LobCreator lobCreator) {
					try {
						lobCreator.setBlobAsBinaryStream(ps, 1, inputStream, (int) file.length());
					} catch (SQLException e) {
						throw new RuntimeException(e.getMessage());
					}
				}
			});
		} catch (Exception e) {
			result = false;
			throw new RuntimeException(e.getMessage());
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
		}
		return result;
	}

	/**
	 * 保存Blob
	 */
	public boolean updateForBlob(String sql, final int length, final InputStream inputStream) {
		log.debug(sql);
		boolean result = true;
		try {
			final LobHandler lobHandler = new DefaultLobHandler();
			this.getJdbcTemplate().execute(sql, new AbstractLobCreatingPreparedStatementCallback(lobHandler) {
				protected void setValues(PreparedStatement ps, LobCreator lobCreator) {
					try {
						lobCreator.setBlobAsBinaryStream(ps, 1, inputStream, length);
					} catch (SQLException e) {
						throw new RuntimeException(e.getMessage());
					}
				}
			});
		} catch (Exception e) {
			result = false;
			throw new RuntimeException(e.getMessage());
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
		}
		return result;
	}

	/**
	 * 
	 * @param sql
	 * @param blobFields
	 * @return
	 */
	public List<Map<String, Object>> queryBlobAsInputStreamList(String sql, String[] blobFields) {
		log.debug(sql);
		final String[] upperBlobFields = new String[blobFields.length];
		for (int i = 0; i < blobFields.length; i++) {
			upperBlobFields[i] = blobFields[i].toString().toUpperCase();
		}
		final List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		final LobHandler lobHandler = new DefaultLobHandler();
		this.getJdbcTemplate().query(sql, new RowMapper<Object>() {
			Map<String, Object> map = null;
			int colCount = 0;
			String colName = "";
			ResultSetMetaData metaData = null;

			public Object mapRow(ResultSet rs, int i) throws SQLException {
				map = new HashMap<String, Object>();
				metaData = rs.getMetaData();
				colCount = metaData.getColumnCount();
				for (int c = 1; c <= colCount; c++) {
					colName = metaData.getColumnName(c);
					if (ArrayUtils.contains(upperBlobFields, colName)) {
						map.put(colName, lobHandler.getBlobAsBinaryStream(rs, c));
					} else {
						map.put(colName, rs.getString(colName));
					}
				}
				result.add(map);
				return result;
			}
		});
		return result;
	}

	/**
	 * 
	 * @param sql
	 * @param blobFields
	 * @return
	 */
	public InputStream queryBlobAsInputStream(String sql, String[] blobFields) {
		log.debug(sql);
		final String[] upperBlobFields = new String[blobFields.length];
		for (int i = 0; i < blobFields.length; i++) {
			upperBlobFields[i] = blobFields[i].toString().toUpperCase();
		}
		final List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		final LobHandler lobHandler = new DefaultLobHandler();
		sql = new StringBuffer("select * from (").append(sql).append(") where rownum < 2").toString();

		this.getJdbcTemplate().query(sql, new RowMapper<Object>() {
			Map<String, Object> map = null;
			int colCount = 0;
			String colName = "";
			ResultSetMetaData metaData = null;

			public Object mapRow(ResultSet rs, int i) throws SQLException {
				map = new HashMap<String, Object>();
				metaData = rs.getMetaData();
				colCount = metaData.getColumnCount();
				for (int c = 1; c <= colCount; c++) {
					colName = metaData.getColumnName(c);
					if (ArrayUtils.contains(upperBlobFields, colName)) {
						map.put(colName, lobHandler.getBlobAsBinaryStream(rs, c));
					} else {
						map.put(colName, rs.getString(colName));
					}
				}
				result.add(map);
				return result;
			}
		});
		if (result.size() > 0) {
			return (InputStream) result.get(0);
		}
		return null;
	}

}
