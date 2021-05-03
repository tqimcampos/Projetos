package com.br.msNovoSalao.Utils.Query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogQueryFilter implements IQueryPaginationFilter{
	  
	  private static final long serialVersionUID = 1L;

	  private String minDataRequisicao;
	  private String maxDataRequisicao;
	  private Integer tamanho;
	  private Integer pagina;
}
