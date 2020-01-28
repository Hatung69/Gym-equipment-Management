package model.tinhtrang;

public class TinhTrang {
	private String maTinhTrang, tenTinhTrang;

	// --------------------------------
	public String getMaTinhTrang() {
		return maTinhTrang;
	}

	public void setMaTinhTrang(String maTinhTrang) {
		this.maTinhTrang = maTinhTrang;
	}

	public String getTenTinhTrang() {
		return tenTinhTrang;
	}

	public void setTenTinhTrang(String tenTinhTrang) {
		this.tenTinhTrang = tenTinhTrang;
	}

	// --------------------------------------
	public TinhTrang(String maTinhTrang, String tenTinhTrang) {
		super();
		this.maTinhTrang = maTinhTrang;
		this.tenTinhTrang = tenTinhTrang;
	}

	public TinhTrang() {
		// TODO Auto-generated constructor stub
	}
	
	 @Override
	    public String toString()  {
	        return this.maTinhTrang+"-"+tenTinhTrang;
	    }
}
