package support.user.model;

import java.io.Serializable;

public class RolModel
  implements Serializable, Comparable<RolModel>
{
  private static final long serialVersionUID = 1L;
  private long id;
  private String nombre;
  private long rolID;
  
  public RolModel()
  {
    this(0L, "", 0L);
  }
  
  public RolModel(long id, String nombre, long rolID)
  {
    this.id = id;
    this.nombre = nombre;
    this.rolID = rolID;
  }
  
  public long getId()
  {
    return this.id;
  }
  
  public void setId(long id)
  {
    this.id = id;
  }
  
  public String getNombre()
  {
    return this.nombre;
  }
  
  public void setNombre(String nombre)
  {
    this.nombre = nombre;
  }
  
  public long getRolID()
  {
    return this.rolID;
  }
  
  public void setRolID(long rolID)
  {
    this.rolID = rolID;
  }
  
  public int hashCode()
  {
   // int prime = 31;
    int result = 1;
    result = 31 * result + (int)(this.id ^ this.id >>> 32);
    result = 31 * result + (this.nombre == null ? 0 : this.nombre.hashCode());
    return result;
  }
  
  public boolean equals(Object obj)
  {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    RolModel other = (RolModel)obj;
    if (this.id != other.id) {
      return false;
    }
    if (this.nombre == null)
    {
      if (other.nombre != null) {
        return false;
      }
    }
    else if (!this.nombre.equals(other.nombre)) {
      return false;
    }
    return true;
  }
  
  public String toString()
  {
    return this.nombre;
  }
  
  public int compareTo(RolModel rol)
  {
    return this.id > rol.getId() ? 1 : this.id == rol.getId() ? 0 : -1;
  }
}
