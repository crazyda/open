package com.axp.domain;

/**
 * Auto-generated: 2017-12-25 15:6:55
 *
 */
public class Genocoding {

    private int status;
    private String message;
    private Double lat;
    private Double lng;
    @Override
	public String toString() {
		return "Genocoding [status=" + status + ", message=" + message
				+ ", lat=" + lat + ", lng=" + lng + "]";
	}
	public Double getLat() {
		return lat;
	}
	public void setLat(Double lat) {
		this.lat = lat;
	}
	public Double getLng() {
		return lng;
	}
	public void setLng(Double lng) {
		this.lng = lng;
	}
	public void setStatus(int status) {
         this.status = status;
     }
     public int getStatus() {
         return status;
     }

    public void setMessage(String message) {
         this.message = message;
     }
     public String getMessage() {
         return message;
     }


}