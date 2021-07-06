package com.model.bcp.api;

import java.util.ArrayList;
import java.util.List;

public class AlgoritmoNumerosPrimos {
	private void psvm() {
		int[] datos = {1, 2, 8, 23, 5, 15, 17, 15};
		List<Integer> result = new ArrayList<>();
		for(int i=0;i<datos.length;i++) {
			Integer numero =datos[i];
			int totalResiduosCero=0;
			for(int j=1; j<=numero;j++) {
				Double residuo =  numero.doubleValue() % j; // 
				if(residuo==0) {
					totalResiduosCero++;
				}
			}
			if(totalResiduosCero==2) {
				result.add(numero);
			}
		}
		
		Integer auxiliar;
	      for(int i = 1; i < result.size(); i++)
	      {
	        for(int j = 0;j < result.size()-i;j++)
	        {
	          if(result.get(j) > result.get(j+1))
	          {
	            auxiliar = result.get(j);
	            result.set(j, result.get(j+1));
				result.set(j + 1, auxiliar);
		      }   
	        }
	      }
		System.out.println("Resultado es: "+result);

	}
}
