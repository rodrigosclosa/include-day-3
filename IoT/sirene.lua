-- Include CI&T IoT module
--local ciot = require("CIOT")
--ciot.SetDebugMode(true)

--##################
--# BEGIN settings #
--##################
local tmr = tmr
local gpio = gpio
local wifi = wifi
local cjson = cjson
local pwm = pwm

local gpio_sirene = 2
local gpio_led_vermelho = 1
local gpio_led_amarelo = 3
local delay_time = 10000

local host = "api.iot.ciandt.com"
local port = 80
local apiurl = "v2/data/{ID}/last"
local secure_conn = 0
local last_id = ""

gpio.mode(gpio_sirene, gpio.OUTPUT)
gpio.mode(gpio_led_vermelho, gpio.OUTPUT)
gpio.mode(gpio_led_amarelo, gpio.OUTPUT)
gpio.write(gpio_sirene, gpio.LOW)
gpio.write(gpio_led_vermelho, gpio.LOW)
gpio.write(gpio_led_amarelo, gpio.LOW)

wifi_SSID = ""
wifi_password = ""

wifi.setmode(wifi.STATION)
wifi.sta.config(wifi_SSID, wifi_password)
wifi.sta.connect()

--################
--# END settings #
--################

tmr.alarm(1, 1000, 1, function() 
    if wifi.sta.getip() == nil then 
        print("Obtendo IP, aguarde...") 
    else 
        print(wifi.sta.status())
        print(wifi.sta.getip())
        tmr.stop(1)

        print("IP configurado. IP: "..wifi.sta.getip())
        --dofile("yourfile.lua")

        tmr.alarm(2, 3000, 1, function()
            get_data()
        end)
    end 
 end)

-- Busca os dados do servico REST da plataforma
function get_data()

  if wifi.sta.status() == 5 then
    tmr.stop(2);
  
    skg=net.createConnection(net.TCP, secure_conn)
    skg:connect(port, host)

    skg:on("connection", function(sck,c)
        skg:send("GET /" .. apiurl .." HTTP/1.1\r\nHost: " .. host .."\r\nConnection: keep-alive\r\nCache-Control: max-age=0\r\nAccept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8\r\nUpgrade-Insecure-Requests: 1\r\nAccept-Encoding: gzip, deflate, sdch\r\nAccept-Language: pt-BR,pt;q=0.8,en-US;q=0.6,en;q=0.4\r\n\r\n")
    end)

    skg:on("receive", function(sck, c)
      if c ~= nil then
      
        json = string.match(c, "{.*}")
        id = string.match(c, "\"id\":(.*),\"sensor")

        if json ~= nil and id ~= nil then
          if last_id ~= id then
            local dados = cjson.decode(json)
    
            if dados ~= nil then
              --for k,v in pairs(dados) do print(k,v) end
              local content = dados["content"]
              --for k,v in pairs(content) do print(k,v) end
              
              if content ~= nil then
                --Verifica os dados retornados para ligar ou desligar os pinos
                local sirene = content.sirene
                local led_vermelho = content.led_vermelho
                local led_amarelo = content.led_amarelo
                
--                print("sirene: ")
--                print(sirene)
--                print("led_vermelho: ")
--                print(led_vermelho)
--                print("led_amarelo: ")
--                print(led_amarelo)
                
                if tonumber(sirene) == 1 then
                  TocarSirene()
                else
                  --print("Desligando sirene... ")
                  DesligarSirene()
                end
                
                if tonumber(led_vermelho) == 1 then
                  --print("Ligando led vermelho... ")
                  BlinkLedRed()
                else
                  --print("Desligando led vermelho... ")
                  OffLedRed()
                end
                
                if tonumber(led_amarelo) == 1 then
                  --print("Ligando led amarelo... ")
                  BlinkLedYellow()
                else 
                  --print("Desligando led amarelo... ")
                  OffLedYellow()
                end
              end
              
            end
            
            last_id = id
          end
         
        end
        
      end
      
    end)
    
    skg:close() -- close the socket
    tmr.start(2);
    
  end
  
end

function TocarSirene()
  local cont = 0
  local times = 10
  pwm.setup(gpio_sirene, 300, 512)
  pwm.start(gpio_sirene)
  
  tmr.alarm(3, 1000, 1, function()
    if cont <= times then
      pwm.setduty(gpio_sirene, 1023)
      tmr.delay(100000)
      pwm.setduty(gpio_sirene, 512)
      cont = cont + 1
    else 
      pwm.stop(gpio_sirene)
      tmr.stop(3)
    end
  end)
end

function DesligarSirene()
  pwm.stop(gpio_sirene)
end

function BlinkLedRed() 
  local cont = 1
  local times = 10
  local lighton = 0
  
  tmr.alarm(4, 1000, 1, function()
    if cont <= times then
      if lighton == 0 then
        lighton = 1
        gpio.write(gpio_led_vermelho, gpio.HIGH)
      else
        lighton = 0
        gpio.write(gpio_led_vermelho, gpio.LOW)
      end
      cont = cont + 1
    else
      --print("Desligando led vermelho... ")
      gpio.write(gpio_led_vermelho, gpio.LOW)
      tmr.stop(4)
    end
  end)
end

function OffLedRed()
  --print("Desligando led vermelho... ")
  gpio.write(gpio_led_vermelho, gpio.LOW)
end 

function BlinkLedYellow() 
  local cont = 1
  local times = 10
  local lighton = 0
  
  tmr.alarm(5, 1000, 1, function()
    if cont <= times then
      if lighton == 0 then
        lighton = 1
        gpio.write(gpio_led_amarelo, gpio.HIGH)
      else
        lighton = 0
        gpio.write(gpio_led_amarelo, gpio.LOW)
      end
      cont = cont + 1
    else
      --print("Desligando led amarelo... ")
      gpio.write(gpio_led_amarelo, gpio.LOW)
      tmr.stop(5)
    end
  end)
end

function OffLedYellow()
  --print("Desligando led amarelo... ")
  gpio.write(gpio_led_amarelo, gpio.LOW)
end
