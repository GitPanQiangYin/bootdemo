package com.boot.bootdemo.dao;

        import com.boot.bootdemo.entity.Paper;
        import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
        import org.springframework.stereotype.Repository;
        import java.util.Optional;

/**
 * @author Administrator
 * @date 2020/7/24 15:44
 */
@Repository
public interface PaperRepository extends ElasticsearchRepository<Paper,String>{

        @Override
        Optional<Paper> findById(String s);
}
