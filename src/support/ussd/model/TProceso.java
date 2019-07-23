package support.ussd.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the t_procesos database table.
 * 
 */
@Entity
@Table(name="t_procesos")
@NamedQuery(name="TProceso.findAll", query="SELECT t FROM TProceso t")
public class TProceso implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="T_PROCESOS_ID_GENERATOR", sequenceName="T_PROCESOS_ID_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="T_PROCESOS_ID_GENERATOR")
	private Long id;

	private String command;

	private String cpu;

	private String mem;

	private String ni;

	private String pid;

	private String pr;

	private String res;

	private String s;

	private String shr;

	private String time;

	private String user;

	private String virt;

	//bi-directional many-to-one association to TTop
	@ManyToOne
	@JoinColumn(name="id_top")
	private TTop TTop;

	public TProceso() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCommand() {
		return this.command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public String getCpu() {
		return this.cpu;
	}

	public void setCpu(String cpu) {
		this.cpu = cpu;
	}

	public String getMem() {
		return this.mem;
	}

	public void setMem(String mem) {
		this.mem = mem;
	}

	public String getNi() {
		return this.ni;
	}

	public void setNi(String ni) {
		this.ni = ni;
	}

	public String getPid() {
		return this.pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getPr() {
		return this.pr;
	}

	public void setPr(String pr) {
		this.pr = pr;
	}

	public String getRes() {
		return this.res;
	}

	public void setRes(String res) {
		this.res = res;
	}

	public String getS() {
		return this.s;
	}

	public void setS(String s) {
		this.s = s;
	}

	public String getShr() {
		return this.shr;
	}

	public void setShr(String shr) {
		this.shr = shr;
	}

	public String getTime() {
		return this.time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getUser() {
		return this.user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getVirt() {
		return this.virt;
	}

	public void setVirt(String virt) {
		this.virt = virt;
	}

	public TTop getTTop() {
		return this.TTop;
	}

	public void setTTop(TTop TTop) {
		this.TTop = TTop;
	}

}