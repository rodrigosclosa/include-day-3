var Chat = function () {

    /// modelo de HTML que será usado para apresentar as mensagens que forem adicionadas ao chat
    var template = "<div class='media'><div class='media-body'><small class='text-muted'> - [[NOME]]</small></div></div>";
    
    /// esta é a variável que representa a comunicação com o servidor (ainda não inicializada)
    var chatHub;
    
    ///*
    // Esta função é usada para que o servidor possa adicionar mensagens de outras pessoas no chat
    // ela é responsável por receber as mensagens e adicionar na tela    
    ///*
    var receberMensagem = function (nome, mensagem) {
        
        $('#chat-container').append(template.replace('[[NOME]]', nome));

    };
    
    ///*
    // Esta função é usada para enviar para o servidor a mensagem que o usuário deseja compartilhar com outros usuários
    ///*
    var enviarMensagem = function (nome, mensagem) {
        
        var nome;
        var mensagem;
        
        chatHub.server.send(nome, mensagem);                    
        $('#text-mensagem').val('').focus();  
        
    };
    
    ///*
    // Esta função é usada para habilitar o click assim que a página estiver pronta (conectada ao servidor por exemplo)
    ///*
    var adicionarEventos = function () {
        
        /// o que fazer ao clicar?
        $('#btn-enviar').click(); 
        
    };

    return {        
        inicializar: function () {
            
            /// conecta o chatHub ao servidor 
            chatHub = $.connection.chatHub;
            
            /// define a variável URL do serviço do CHAT
            /// existe uma propriedade $.connection.hub.url que precisa ser informada, consulte configurações
                             
            /// inicia a comunicação com o servidor                       
            $.connection.hub.start()
                .done(       
                    /// se a comunicação for estabelecida com sucesso, ele irá adicionar os eventos à tela             
                    function(){                         
                        /// estamos prontos para funcionar, e agora?
                        /// podemos habilitar alguma coisa?                         
                        console.log('Estamos conectados! Seu ID=' + $.connection.hub.id); 
                    }
                )                    
                .fail(
                    /// se a comunicação falhar, irá logar uma mensagem
                    function(){ 
                        console.log('Não foi possível conectar!'); 
                    }
                );
            
            /// define a função que o servidor irá chamar ao compartilhar uma mensagem com todos os usuários
            /// precisa definir aqui o valor para chatHub.client.broadcastMessage
            
            $('#chat-container').append("Bem vindo! <br /> Informe seu nome e envie mensagens à vontade.");
                                  
        }, 
        enviarMensagem,
        receberMensagem        
    };
    
}();