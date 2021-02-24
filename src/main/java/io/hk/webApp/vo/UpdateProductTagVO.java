package io.hk.webApp.vo;

public class UpdateProductTagVO {
    //商品id
    private String _id;
    //1是推荐 2是热销 3是新品
    private String tagType;

    @Override
    public String toString() {
        return "UpdateProductTagVO{" +
                "_id='" + _id + '\'' +
                ", tagType='" + tagType + '\'' +
                '}';
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTagType() {
        return tagType;
    }

    public void setTagType(String tagType) {
        this.tagType = tagType;
    }
}
