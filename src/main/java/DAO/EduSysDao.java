package DAO;

import java.util.List;

abstract public class EduSysDao<EntityType, KeyType> {
    abstract public void insert(EntityType entity);
    abstract public void update(EntityType entity);
    abstract public void delete(KeyType id);
    abstract public List<EntityType> selectAll();
    abstract public EntityType selectById(KeyType id);
    abstract public List<EntityType> selectBySql(String sql, Object... args);
}
