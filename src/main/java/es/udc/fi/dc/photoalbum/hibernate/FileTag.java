package es.udc.fi.dc.photoalbum.hibernate;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "FILE_TAG")
@SuppressWarnings("serial")
public class FileTag implements Serializable {

    private Integer id;
    private File file;
    private String tag;

    public FileTag() {

    }

    public FileTag(File file, String tag) {
        this.file = file;
        this.tag = tag;
    }

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "FILE_ID")
    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    @Column(name = "TAG")
    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

}
