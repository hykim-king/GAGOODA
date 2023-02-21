package com.example.gagooda_project.dto;

import lombok.Data;

import java.util.Map;

@Data
public class PagingDto {
    private int page=1;
    private int rows=5;
    private int startRow;
    private String orderField;
    private String direct="DESC";
    private int totalRows; //레코드 전체 수
    private int totalPages;//페이지 전체 수
    private boolean prev;//현재페이지가 1이면 false
    private boolean next;//현재페이지가 마지막이면 false
    private int prevPage;
    private int nextPage;
    private int pageCount=5;//화면에 보일 페이지 수  5 6 [7] 8 9 , 1 [2] 3 4 5 ,  8 9 10 11 [12]
    private  int startPage; //화면에 보일 페이지 수에서 가장 앞 페이지 숫자
    private int endPage; // 화면에 보일 페이지 수에서 가장 마지막 페이지 숫자
    private String queryString;
    public PagingDto() { //dto를 컨트롤러의 파라터로 사용할때 생성에 정의된 필드가 required=true 로 정의된다.
    }
    public PagingDto(int page, int rows) {
        this.page=page;
        this.rows = rows;
    }

    public PagingDto(int page, int rows, String orderField, String direct) {
        this.page = page;
        this.rows = rows;
        this.orderField = orderField;
        this.direct = direct;
    }

    public void setQueryString(Map<String, String[]> queryMap) {
        StringBuilder queryString= new StringBuilder();
        String and="?";
        for (String name :queryMap.keySet()){
            if(!name.equals("page")){
                for (String val : queryMap.get(name)){
                    queryString.append(and).append(name).append("=").append(val);
                    and="&";
                }
            }
        }
        System.out.println("queryString: "+queryString);
        this.queryString = queryString.toString();
    }

    public void setTotalRows(int totalRows) {
        this.startRow=(this.page-1)*this.rows;
        this.totalRows = totalRows;
        this.totalPages=totalRows/rows+((totalRows%rows>0)?1:0);
        this.startPage=this.page-(pageCount-1)/2;
        if(this.startPage<1)this.startPage=1;
        this.endPage=this.startPage+this.pageCount-1;
        if(this.endPage>this.totalPages){
            this.startPage-=(this.endPage-this.totalPages);
            if(this.startPage<1)this.startPage=1;
            this.endPage=this.totalPages;
        }
        this.next= this.page != this.totalPages;
        this.prev= this.page > 1;
        this.nextPage=this.page+1;
        this.prevPage=this.page-1;
    }
}
