package fun.discord.bot.service.cat.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "breeds",
            "id",
            "url",
            "width",
            "height"
    })
    public class CatResponse {

        @JsonProperty("breeds")
        private List<Object> breeds = null;
        @JsonProperty("id")
        private String id;
        @JsonProperty("url")
        private String url;
        @JsonProperty("width")
        private Integer width;
        @JsonProperty("height")
        private Integer height;

        @JsonProperty("breeds")
        public List<Object> getBreeds() {
            return breeds;
        }

        @JsonProperty("breeds")
        public void setBreeds(List<Object> breeds) {
            this.breeds = breeds;
        }

        @JsonProperty("id")
        public String getId() {
            return id;
        }

        @JsonProperty("id")
        public void setId(String id) {
            this.id = id;
        }

        @JsonProperty("url")
        public String getUrl() {
            return url;
        }

        @JsonProperty("url")
        public void setUrl(String url) {
            this.url = url;
        }

        @JsonProperty("width")
        public Integer getWidth() {
            return width;
        }

        @JsonProperty("width")
        public void setWidth(Integer width) {
            this.width = width;
        }

        @JsonProperty("height")
        public Integer getHeight() {
            return height;
        }

        @JsonProperty("height")
        public void setHeight(Integer height) {
            this.height = height;
        }
    }
