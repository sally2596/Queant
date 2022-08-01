package com.ssafy.queant.model.entity.content;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.SessionFactory;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;

@Data
//@Entity
//@Table
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Content {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int content_id;
    @Column(nullable = false)
    private String member_id;
    @Column(nullable = false)
    private String title;
    @Lob
    @Column(nullable = false)
    private Blob content;


    public Blob getContent() {
        return content;
    }

    public void setPhoto(Blob content) {
        this.content = content;
    }

    /**
     * Returns the photo Input Stream.
     * @return InputStream
     * @throws SQLException e
     */
    public InputStream getContentContent() throws SQLException {
        if (getContent() == null) {
            return null;
        }

        return getContent().getBinaryStream();
    }

    /**
     *
     * @param sourceStream - Photo source input stream
     * @throws IOException e
     */
    @Transactional
    public void setContentContent(InputStream sourceStream, SessionFactory sessionFactory) throws IOException {
        setPhoto(sessionFactory.getCurrentSession().getLobHelper().createBlob(sourceStream, 100L));
    }

}
