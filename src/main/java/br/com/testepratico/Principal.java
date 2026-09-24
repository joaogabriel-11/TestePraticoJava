/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package br.com.testepratico;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.time.Period;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.util.Comparator;
/**
 *
 * @author joao gabriel
 */
//3 - classe principal
public class Principal {

    public static void main(String[] args) {
        List<Funcionario> funcionarios = new ArrayList<>();
        
        //3.1 - cadastro de todos os funcionarios
        funcionarios.add(new Funcionario("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador"));
        funcionarios.add(new Funcionario("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador"));
        funcionarios.add(new Funcionario("Caio", LocalDate.of(1961, 5, 2), new BigDecimal("9836.14"), "Coordenador"));
        funcionarios.add(new Funcionario("Miguel", LocalDate.of(1988, 10, 14), new BigDecimal("19119.88"), "Diretor"));
        funcionarios.add(new Funcionario("Alice", LocalDate.of(1995, 1, 5), new BigDecimal("2234.68"), "Recepcionista"));
        funcionarios.add(new Funcionario("Heitor", LocalDate.of(1999, 11, 19), new BigDecimal("1582.72"), "Operador"));
        funcionarios.add(new Funcionario("Arthur", LocalDate.of(1993, 3, 31), new BigDecimal("4071.84"), "Contador"));
        funcionarios.add(new Funcionario("Laura", LocalDate.of(1994, 7, 8), new BigDecimal("3017.45"), "Gerente"));
        funcionarios.add(new Funcionario("Heloísa", LocalDate.of(2003, 5, 24), new BigDecimal("1606.85"), "Eletricista"));
        funcionarios.add(new Funcionario("Helena", LocalDate.of(1996, 9, 2), new BigDecimal("2799.93"), "Gerente"));
        
        //3.2 - remoção do funcionario joao
        funcionarios.removeIf(funcionario -> funcionario.getNome().equals("João"));
        
        //formatar data e salario
        DateTimeFormatter dataFormatada = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        NumberFormat numeroFormatado = NumberFormat.getNumberInstance(Locale.forLanguageTag("pt-BR"));
        
        numeroFormatado.setMinimumFractionDigits(2);
        numeroFormatado.setMaximumFractionDigits(2);
        
        //3.3 - print dos funcionarios
        System.out.println("----- FUNCIONARIOS -----");
        for(Funcionario funcionario : funcionarios){
            System.out.println("Nome: " + funcionario.getNome() 
                    + " | Data de nascimento: " + funcionario.getDataNascimento().format(dataFormatada) 
                    + " | Salario: " + numeroFormatado.format(funcionario.getSalario()) 
                    + " | Funcao: " + funcionario.getFuncao());
        }
        
        
        //3.4 - aumento dos 10%
        BigDecimal aumento = new BigDecimal("1.10");
        
        for(Funcionario funcionario : funcionarios){
            BigDecimal novoSalario = funcionario.getSalario().multiply(aumento).setScale(2, RoundingMode.HALF_UP);
            
            funcionario.setSalario(novoSalario);
        }
        
        Map<String, List<Funcionario>> funcionariosPorFuncao = new LinkedHashMap<>();
        
        //3.5 - agrupa funcionario por funcao
        for(Funcionario funcionario : funcionarios){
            funcionariosPorFuncao.computeIfAbsent(funcionario.getFuncao(), chave -> new ArrayList<>()).add(funcionario);
        }
        
        //3.6 - print dos funcionarios por funcao
        System.out.println("\n----- Funcionarios por funcao -----");
        for(Map.Entry<String, List<Funcionario>> grupo : funcionariosPorFuncao.entrySet()){
            System.out.println("Funcao: " + grupo.getKey());
            
            for(Funcionario funcionario : grupo.getValue()){
                System.out.println(" - " + funcionario.getNome());
            }
        }
        
        //3.8 - print dos funcionarios com aniversario mes 10 ou 12
        System.out.println("\n----- Aniversariantes dos meses 10 e 12 -----");
        for(Funcionario funcionario : funcionarios){
            int mes = funcionario.getDataNascimento().getMonthValue();
            
            if(mes == 10 || mes == 12){
                System.out.println(funcionario.getNome() + " | Data de nascimento: " + funcionario.getDataNascimento().format(dataFormatada));
            }
        }
        
        //3.9 - print do funcionario mais velho(nome e idade)
        System.out.println("\n----- Funcionario mais velho -----");
        Funcionario maisVelho = funcionarios.get(0);
        for(Funcionario funcionario : funcionarios){
            if(funcionario.getDataNascimento().isBefore(maisVelho.getDataNascimento())){
                maisVelho = funcionario;
            }
        }
        int idade = Period.between(maisVelho.getDataNascimento(), LocalDate.now()).getYears();
        System.out.println("Nome: " + maisVelho.getNome() + " | Idade: " + idade + " anos"); 
        
        //3.10 - print dos funcionarios em ordem alfabetica
        funcionarios.sort(Comparator.comparing(Funcionario::getNome));
        System.out.println("\n----- Funcionarios em ordem alfabetica -----");
        for(Funcionario funcionario : funcionarios){
            System.out.println(funcionario.getNome());
        }
        
        //3.11 - print do salario total
        BigDecimal total = BigDecimal.ZERO;
        System.out.println("\n----- Total dos salarios -----");
        for(Funcionario funcionario : funcionarios){
            total = total.add(funcionario.getSalario());
        }
        System.out.println("Total: " + numeroFormatado.format(total));
        
        //3.12 - print de quantos salarios minimos cada funcionario recebe
        BigDecimal salarioMinimo = new BigDecimal("1212.00");
        System.out.println("\n----- Quantidade de salarios minimos -----");
        for(Funcionario funcionario : funcionarios){
            BigDecimal qtd = funcionario.getSalario().divide(salarioMinimo, 2, RoundingMode.HALF_UP);
            System.out.println(funcionario.getNome() + " | Salarios minimos: " + numeroFormatado.format(qtd));
        }
    }
}