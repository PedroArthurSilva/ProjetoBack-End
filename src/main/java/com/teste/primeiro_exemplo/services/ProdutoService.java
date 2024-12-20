package com.teste.primeiro_exemplo.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.internal.bytebuddy.dynamic.DynamicType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.teste.primeiro_exemplo.model.Produto;
import com.teste.primeiro_exemplo.model.exception.ResouceNotFoundException;
import com.teste.primeiro_exemplo.repository.ProdutoRepository;
import com.teste.primeiro_exemplo.repository.ProdutoRepository_old;
import com.teste.primeiro_exemplo.shared.ProdutoDTO;

@Service
public class ProdutoService {
  
  @Autowired
  private ProdutoRepository produtoRepository;

   
  /**
   * Metodo para retornar uma lista de produtos.
   * @return Lista de produtos.
   */
  public List<ProdutoDTO> obterTodos(){
   // Retorna uma lista de produto model.
    List<Produto> produtos =  produtoRepository.findAll();

    return produtos.stream().map(produto -> new ModelMapper().map(produto, ProdutoDTO.class)).collect(Collectors.toList());

  }
    /* 
  /**
   * Metodo que retorna o produto enocontrado pelo seu id.
   * @param id do produto que será localizado.
   * @return Retorna um produto caso tenha encontrado.
   */
  public Optional<ProdutoDTO> obterPorId(Integer id) {
    // Obtendo optional de produto por id.
     Optional<Produto> produto =  produtoRepository.findById(id);

    // Se naõ encontrar, lança exception.
     if(produto.isEmpty()) {
      throw new ResouceNotFoundException("Produto com id: " + id + " não encontrado");
     }

     // Convertendo o meu optional de produto em um produtoDTO.
     ProdutoDTO dto = new ModelMapper().map(produto.get(),ProdutoDTO.class);

     // Criando e retornando um optional de produtoDTO.
     return Optional.of(dto);
  }

  /**
   * Metodo para adicionar produto na lista.
   * @param produto que será adicionado.
   * @return Retorna o produto que foi adicionado na lista.
   */
  public ProdutoDTO adicionar(ProdutoDTO produtoDto) {
    // Removendo o id para conseguir fazer o cadastro
    produtoDto.setId(null);

    // Criar um objeto de mapeamento.
ModelMapper mapper = new ModelMapper();

    // Converter o nosso ProdutoDTO em um Produto
Produto produto = mapper.map(produtoDto, Produto.class);
    // Salvar o Produto no banco
produto = produtoRepository.save(produto);

produtoDto.setId(produto.getId());
    // Retornar o ProdutoDTO atualizadoo.
    return produtoDto;
  }

  /**
   * Metodo para deletar o produto por id.
   * @param id do produto a ser deletado.
   */
  public void deletar(Integer id) {
    // Verifica se o produto existe antes de tentar deletar
    Optional<Produto> produto = produtoRepository.findById(id);
// Se não existir lança uma exception
    if(produto.isEmpty()) {
      throw new ResouceNotFoundException("Não foi possível deletar o produto com o id: " + id + " - Produto não existe");
    }
    // Deleta o produto por id.
    produtoRepository.deleteById(id);
  }

  /**
   * Metodo para atualizar o produto na lista.
   * @param produto que será atualizado
   * @param id do produto
   * @return Retorna o produto após. atualizar a lista.
   */
  public ProdutoDTO atualizar(Integer id, ProdutoDTO produtoDto) {
    // Passar o id para o prordutoDto
produtoDto.setId(id);
    // Criar um objeto de mapeamento 
ModelMapper mapper = new ModelMapper();

    // Converter o ProdutoDTO em um Protudo.
Produto produto = mapper.map(produtoDto, Produto.class);
    // Atualizar o produto no Banco de dados.
produtoRepository.save(produto);
    // Retornar o produtoDto atualizado.
return produtoDto;
  }
  
}
