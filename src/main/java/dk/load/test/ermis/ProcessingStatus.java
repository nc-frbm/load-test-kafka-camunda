package dk.load.test.ermis;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GeneratorType;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "t_processing_status")
@DynamicUpdate
public class ProcessingStatus {
    public Long getSid() {
        return sid;
    }

    public void setSid(Long sid) {
        this.sid = sid;
    }

    public String getDeclaration_id() {
        return declaration_id;
    }

    public void setDeclaration_id(String declaration_id) {
        this.declaration_id = declaration_id;
    }

    public Long getDeclaration_version() {
        return declaration_version;
    }

    public void setDeclaration_version(Long declaration_version) {
        this.declaration_version = declaration_version;
    }

    public Long getProcessing_status_type() {
        return processing_status_type;
    }

    public void setProcessing_status_type(Long processing_status_type) {
        this.processing_status_type = processing_status_type;
    }

    @Id
    @GeneratedValue
    private Long sid;
    private String declaration_id;
    private Long declaration_version;
    private Long processing_status_type;
}
