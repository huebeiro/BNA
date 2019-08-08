
# BnaSDK Cordova *0.2.6*

O BNA-SDK é um projeto Ericsson para coleta de dados celulares baseados em localização GPS.

## Getting Started

Para iniciar, é necessário obter o `cordova-plugin-bna` que deverá ser disponibilizado pela Ericsson.

### Prerequisites

A fim de manter a privacidade, a Ericsson não se utiliza de repositórios como NPM, Bintray, Sonatype, Jitpack, etc.
Por este motivo, é necessário realizar, apenas nesta versão, a configuração do ambiente para total funcionamento
da biblioteca.

O BnaSDK oferece suporte a partir do Android SDK 16.
Por este motivo, sua aplicação deverá ter suporte mínimo para versão 16 do Android SDK.

```
android {
	...
    minSdkVersion 16
    ...
}
```

**ATENÇÃO:**
Se houver conflito nas versões das bibliotecas com.android.support, 

![All com.android.support libraries problem](https://cdn-images-1.medium.com/max/1000/1*vx7iP2SD6IlTrAoCJCr3oQ.png)
apenas adicione a versão mais recente da biblioteca com.android.design.

Até o release desta versão, a versão mais recente é:

```
implementation 'com.android.support:design:27.1.1'
```

Se houver problemas com MultiDex devido ao limite de quantidade de métodos ter sido ultrapassada, siga este procedimento para habilitar o suporte a **MultiDex** clicando [AQUI](https://developer.android.com/studio/build/multidex.html)

Adicione ao seu arquivo `AndroidManifest.xml` os seguintes meta-dados:

```
<application>
...
    <meta-data
        android:name="com.ericsson.bna.application.name"
        android:value="Your App Name"/>

    <meta-data
        android:name="com.ericsson.bna.partner.name"
        android:value="Your Partner Name"/>
    
    <meta-data
        android:name="com.ericsson.bna.application.domain"
        android:value="Your App Domain"/>
    
    <meta-data
        android:name="com.ericsson.bna.application.key"
        android:value="Your App Key"/>

    <service
        android:name="com.evernote.android.job.gcm.PlatformGcmService"
        android:enabled="true"
        tools:replace="android:enabled" />

</application>
```



**ATENÇÃO:**
Se sua aplicação  é distribuída para domínios diferentes, identifique o domínio informando este meta-dado: `com.ericsson.bna.application.domain` no arquivo `AndroidManifest.xml`, conforme exemplificado acima.

Para mais detalhes a respeito do Domínio de Aplicação, contate a Ericsson.

### Installing and Running BNA Service

Após configurado o ambiente, baixe e instale o plugin `cordova-plugin-bna` no projeto.

Siga estes passos:

Baixe o plugin e mova a pasta para o diretório desejado.
Exemplo: C\Users\Me\Desktop ou /home/me/desktop/

Acesse a pasta raiz do seu projeto pelo prompt de comando e utilize o comando:

`cordova plugin add /diretorio/do/plugin/BNASdkCordova/`

***Caso esteja usando outra distribuição como, por exemplo, PhoneGap execute o comando equivalente***

Em seguida use o comando para construir a aplicação:

`cordova clean android`
`cordova build android`

**Após o build da aplicação com sucesso, continue com os passos a seguir.**

No Arquivo .JS de inicialização da aplicação, após concedidas as permissões necessárias inicie o BnaSDK:

```
onDeviceReady: function() {

    // ** após permissões concedidas com sucesso

    cordova.exec(
        function(success) { },              //success callback
        function(error) { },                //error callback
        "BNASdkCordova",                    //class name
        "go",                               //action name
        []
    );

}
```

**ATENÇÃO:**
Caso sua aplicação ofereça suporte a versões Marshmallow ou superior, solicite as permissões em tempo de execução:

```

onDeviceReady: function() {

    var permissions = cordova.plugins.permissions;

    var list = [
        permissions.READ_PHONE_STATE,
        permissions.ACCESS_COARSE_LOCATION,
        permissions.ACCESS_FINE_LOCATION,
        permissions.RECORD_AUDIO,
        permissions.WRITE_EXTERNAL_STORAGE,
        permissions.READ_CONTACTS
        permissions.FOREGROUND_SERVICE
    ];

}

```

**ATENÇÃO:**
Caso o usuário negue alguma permissão, verifique novamente se é possível solicitar quando a aplicação for executada pois algumas features do serviço BNA irão apenas funcionar se todas as permissões forem concedidas.

Caso o usuário não permita a localização GPS, por exemplo, o serviço do BNA não deverá ser iniciado. E, caso esteja em execução, deverá ser parado utilizando o método
 `
 cordova.exec(
        function(success) { },              //success callback
        function(error) { },                //error callback
        "BNASdkCordova",                    //class name
        "stop",                             //action name
        []
    );
`.

Sempre que possível, requisite as permissões negadas para que o seu serviço BNA obtenha coletas de dados corretamente.

Para saber mais a respeito de Requisição de Permissões Android, clique [AQUI](https://developer.android.com/training/permissions/requesting.html)

## Built With

* [RxJava](https://github.com/ReactiveX/RxJava) - Reactive Extensions for the JVM used
* [ORMLite](http://ormlite.com/) - Object Relational Mapping used
* [Google PubSub](https://cloud.google.com/pubsub/) - Used to ingest event streams
* [Android Job](https://github.com/evernote/android-job) - Used to Jobs Management

## Authors

* **Antonio Marcos** - [Email](amoj@ecomp.poli.br)
