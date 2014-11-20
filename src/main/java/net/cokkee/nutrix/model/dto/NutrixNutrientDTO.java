package net.cokkee.nutrix.model.dto;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author drupalex
 */
@XmlRootElement
public class NutrixNutrientDTO extends NutrixAbstractDTO {
    
    private String id;
    private String code;
    private String name;
    private String description;

    public NutrixNutrientDTO() {
        super();
    }

    public NutrixNutrientDTO(String code, String name, String description) {
        this();
        this.code = code;
        this.name = name;
        this.description = description;
    }

    /**
     * This constructor is used for testing setup
     * @param name
     * @param description
     */
    public NutrixNutrientDTO(String id, String code, String name, String description) {
        this(code, name, description);
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlRootElement
    public static class Pack {

        private Integer total = null;

        private List<NutrixNutrientDTO> collection = null;

        public Integer getTotal() {
            return total;
        }

        public void setTotal(Integer total) {
            this.total = total;
        }

        public List<NutrixNutrientDTO> getCollection() {
            return collection;
        }

        public void setCollection(List<NutrixNutrientDTO> collection) {
            this.collection = collection;
        }
    }
}
