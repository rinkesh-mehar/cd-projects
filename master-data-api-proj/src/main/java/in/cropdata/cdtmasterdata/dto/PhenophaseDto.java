package in.cropdata.cdtmasterdata.dto;

public class PhenophaseDto {

	private String PhenophaseName;

	private Integer PhenophaseStart;

	private Integer PhenophaseEnd;

	private Integer span;

	private String imageUrl;

	public String getPhenophaseName() {
		return PhenophaseName;
	}

	public void setPhenophaseName(String phenophaseName) {
		PhenophaseName = phenophaseName;
	}

	public Integer getPhenophaseStart() {
		return PhenophaseStart;
	}

	public void setPhenophaseStart(Integer phenophaseStart) {
		PhenophaseStart = phenophaseStart;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Integer getSpan() {
		return span;
	}

	public void setSpan(Integer span) {
		this.span = span;
	}

	public Integer getPhenophaseEnd() {
		return PhenophaseEnd;
	}

	public void setPhenophaseEnd(Integer phenophaseEnd) {
		PhenophaseEnd = phenophaseEnd;
	}

}
