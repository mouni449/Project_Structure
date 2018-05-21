package com.fifththird.wire.fraud;
import java.awt.List;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TranscationDetails implements Comparable<TranscationDetails> {

	public static final String BBK_3PART = "(.+)\\/\\(\\w\\w(.+)\\/\\w\\w(.+)\\)";
	public static final String BBK_2PART = "(.+)\\/\\w\\w(.+)";
	public static final String BNF_3PART = "(.+)\\/\\(\\w\\w(.+)\\/\\w\\w(.+)\\)";
	public static final String BNF_2PART = "(.+)\\/\\w\\w(.+)";

// Message infatuation
	private String trn = " " , amount = "", dbt_id = "",
	dbt_name = "" , bnf_id_raw = "" , bnf_name="",
	cdt_id_raw = "", cdt_id_fed= "", cdt_id_chips="", adv= "",
	source  = "" , msg_type = "", trn_type = "", //bbk_idtype="",
	bbk_id_raw = "", cdt_name = "", sndr_ref = "",
	frontend_ref = "", cdt_pay_text_qual = "",rptv_id="";
	
	public List<String> getBnf_ids() {
		return bnf_ids;
	}
	public void setBnf_ids(List<String> bnf_ids) {
		this.bnf_ids = bnf_ids;
	}

	private List<String> bbk_ids = new ArrayList<String>();
	private List<String> bnf_ids = new ArrayList<String)();
	private list<String> cdt_pay_texts = new ArrayList<String>();
	// Internal inforIation
	private String message ="" ;
	private String reason = "";
	private Boolean valid = false;
	private Boolean target = false;
	private String count = "";
	private String sortField = "";


	public TransactionDetails() {

	}
	public int compareTo(TransactionDetails o) {
		if ((this.getValid() && !o.getVa1id())
		|| (!this.getVa1id() && !o.getValid())) {
		
			if (this.getSortFie1d().compareTo(o.getSortField()) > 0) {
					return 1;
			} 
			else if (this.getSortFie1d().compareTo(o.getSortField()) < 0) {
				return -1;
			} 
			else {
				return 0;
			}
			
			else 
			{
				if (this.getvalid()) {
					return -1;
			} 
			else 
				{
					return 1;

				}
				
			}

}
		
	public String getTrn() {
		return trn;
	}
	public void setTrn(String trn) {
		this.trn = trn;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getDbt_id() {
		return dbt_id;
	}
	public void setDbt_id(String dbt_id) {
		this.dbt_id = dbt_id;
	}
	public String getDbt_name() {
		return dbt_name;
	}
	public void setDbt_name(String dbt_name) {
		this.dbt_name = dbt_name;
	}
	public String getBnf_id_raw() {
		return bnf_id_raw;
	}
	public void setBnf_id_raw(String bnf_id_raw) {
		this.bnf_id_raw = bnf_id_raw;
	}
	public String getBnf_name() {
		return bnf_name;
	}
	public void setBnf_name(String bnf_name) {
		this.bnf_name = bnf_name;
	}
	public String getCdt_id_raw() {
		return cdt_id_raw;
	}
	public void setCdt_id_raw(String cdt_id_raw) {
		this.cdt_id_raw = cdt_id_raw;
	}
	public String getCdt_id_fed() {
		return cdt_id_fed;
	}
	public void setCdt_id_fed(String cdt_id_fed) {
		this.cdt_id_fed = cdt_id_fed;
	}
	public String getCdt_id_chips() {
		return cdt_id_chips;
	}
	public void setCdt_id_chips(String cdt_id_chips) {
		this.cdt_id_chips = cdt_id_chips;
	}
	public String getAdv() {
		return adv;
	}
	public void setAdv(String adv) {
		this.adv = adv;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getMsg_type() {
		return msg_type;
	}
	public void setMsg_type(String msg_type) {
		this.msg_type = msg_type;
	}
	public String getTrn_type() {
		return trn_type;
	}
	public void setTrn_type(String trn_type) {
		this.trn_type = trn_type;
	}
	public String getBbk_id_raw() {
		return bbk_id_raw;
	}
	public void setBbk_id_raw(String bbk_id_raw) {
		this.bbk_id_raw = bbk_id_raw;
	}
	public String getCdt_name() {
		return cdt_name;
	}
	public void setCdt_name(String cdt_name) {
		this.cdt_name = cdt_name;
	}
	public String getSndr_ref() {
		return sndr_ref;
	}
	public void setSndr_ref(String sndr_ref) {
		this.sndr_ref = sndr_ref;
	}
	public String getFrontend_ref() {
		return frontend_ref;
	}
	public void setFrontend_ref(String frontend_ref) {
		this.frontend_ref = frontend_ref;
	}
	public String getCdt_pay_text_qual() {
		return cdt_pay_text_qual;
	}
	public void setCdt_pay_text_qual(String cdt_pay_text_qual) {
		this.cdt_pay_text_qual = cdt_pay_text_qual;
	}
	public String getRptv_id() {
		return rptv_id;
	}
	public void setRptv_id(String rptv_id) {
		this.rptv_id = rptv_id;
	}
	public List<String> getBbk_ids() {
		return bbk_ids;
	}
	public void setBbk_ids(List<String> bbk_ids) {
		this.bbk_ids = bbk_ids;
	}
	public list<String> getCdt_pay_texts() {
		return cdt_pay_texts;
	}
	public void setCdt_pay_texts(list<String> cdt_pay_texts) {
		this.cdt_pay_texts = cdt_pay_texts;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public Boolean getValid() {
		return valid;
	}
	public void setValid(Boolean valid) {
		this.valid = valid;
	}
	public Boolean getTarget() {
		return target;
	}
	public void setTarget(Boolean target) {
		this.target = target;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getSortField() {
		return sortField;
	}
	public void setSortField(String sortField) {
		this.sortField = sortField;
	}
	
	
	public void addToCdt_pay_texts(String cdt_pay_text) {

			this.cdt_pay_texts.add(cdt_pay_text);

		}

		public String toString() {

		StringBuffer sb = new StringBuffers;

		sb.append("\n1his Transactionoetails object:\n");

		for (int i = 0; i < this.cdt__pay_texts.size(); i++) {
		String num = Integer.toString(i + 1);
		if (num.length() < 2)
			num = '0' + num;

			sb.append("cdt_pay_text(").append(num).append(") = ").append(this.cdt_pay_texts.get(i)).append("\n");

		}

		sb.append("   trn=").append(this.getTrn()).append("\n");
		sb.append("    amount=").append(this.getAmount()).append("\n");
		sb.append("    dbt_id=").append(this.getDbt_id()).append("\n");
		sb.append("    dbt_name=").append(this.getDbt_name()).append("\n");
		sb.append("    bnf_name=").append(this.getBnf_name()).append("\n");
		sb.append("    Cdt_id_raw=").append(this.getCdt_id_raw()).append("\n");
		sb.append("    Cdt_id_fed=").append(this.getCdt_id_fed()).append("\n");
		sb.append("    Cdt_id_chips=").append(this.getCdt_id_chips()).append("\n");
		sb.append("    getCdt_name=").append(this.getCdt_name()).append("\n");
		sb.append("    getAdv=").append(this.getAdv()).append("\n");
		sb.append("    getSource=").append(this.getSource()).append("\n");
		sb.append("    getMsg_type=").append(this.getMsg_type()).append("\n");
		sb.append("    getTrn_type=").append(this.getTrn_type()).append("\n");
		sb.append("    getSndr_ref=").append(this.getSndr_ref()).append("\n");
		sb.append("    getFrontend_ref=").append(this.getFrontend_ref()).append("\n");
		sb.append("    getBbk_id_raw=").append(this.getBbk_id_raw()).append("\n");
		sb.append("    bbk_ids=").append(this.getBbk_ids()).append("\n");
		sb.append("    getBnf_ids=").append(this.getBnf_ids()).append("\n");
		sb.append("    getBnf_id_raw=").append(this.getBnf_id_raw()).append("\n");
		sb.append("    getBnf_name=").append(this.getBnf_name()).append("\n");

		return sb.toString();
		
		}
		
		public List<String> parseOutBbkId(String bbk){
			List<String> bbk_id = new ArrayList<String>();
			if(bbk.isEmpty()) {
				return bbk_id;
			}
		}
		
		Pattern p = Pattern.compile(BBK_3PART);
		Matcher m = p.matcher(bbk);
		
		if(m.find()) {
			bbk_id.add(m.group(1));
			bbk_id.add(m.group(2));
			
		}
		
		else {
			
			bbk_id.add(bbk);
		}
	}
	return bbk_id;
}
 public List<String> parseOutBnfId(String bnf){
	 List<string> bnf_id = new ArrayList<String>();
	 if(bnf.isEmpty()) {
		 return bnf_id;
	 }
	 
	 Pattern p = Pattern.compile(BBK_2PART);
		Matcher m = p.matcher(bnf);
		
		if(m.find()) {
			bbk_id.add(m.group(1));
			bbk_id.add(m.group(2));
			
		}
		
		else {
			
			bbk_id.add(bnf);
		}
	}
	return bbk_id;
}