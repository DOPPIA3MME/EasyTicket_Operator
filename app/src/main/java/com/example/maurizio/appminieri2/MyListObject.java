package com.example.maurizio.appminieri2;


//Gli oggetti che creo avranno solo nome e immagine
public class MyListObject {

   // private int image;
   private String image;
    private String name;
    private String tipologia;
    private String data;
    private String localita;
    private float costo;
    private String id;
    private String descrizione;
    private String indirizzo;
    private String sotto_tipo;
    private boolean put_in_shop;

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public void setTipologia(String tipologia) { this.tipologia = tipologia; }
    public String getTipologia() { return tipologia; }

    public void setData(String data) { this.data = data; }
    public String getData() { return data; }

    public void setLocalita(String localita) { this.localita = localita; }
    public String getLocalita() { return localita; }

    public void setCosto(float costo) { this.costo = costo; }
    public float getCosto() {
        return costo;
    }

    public void setId(String id) { this.id = id; }
    public String getId() { return id; }

    public void setDescrizione(String descrizione){ this.descrizione = descrizione;}
    public String getDescrizione() { return descrizione; }

    public void setIndirizzo(String indirizzo){ this.indirizzo = indirizzo;}
    public String getIndirizzo() { return indirizzo; }

    public void setSotto_tipologia(String sotto_tipo){ this.sotto_tipo = sotto_tipo;}
    public String getSotto_tipologia() { return sotto_tipo; }

    public void setPutShop(boolean f){this.put_in_shop=f;}
    public boolean getPutShop(){return put_in_shop;}

    public MyListObject getThisObject(){return this;}

    public String toString(){
        return "{localita="+localita+", data="+data+", costo="+costo+", nome="+name+", id="+id+", tipologia="+tipologia+", Carrello="+put_in_shop+"}";
    }

}