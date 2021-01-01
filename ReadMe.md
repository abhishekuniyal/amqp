******RABBITMQ ********
* Message Broker that implements Advanced Message Queuing Protocol
* AMQP standarizes messaging using Producers,Brokers, Consumers
* Messaging increases loose coupling and scalability



producer can send routing key to exchnge. And exchange which is binded with the queues might decide based on routingkey==binding key AND EXCAHNGE TYPE.


						      Queue2
	    routing key		     binding 
Producer  -------------> Excahange  ------------------------>   Queue1 
			 	     binding key
						      Queue3


-------------------------------------------------------
Exchange Type   | Description
-------------------------------------------------------		|
FANOUT		| It will ignore the routing key and sends messgae to all Queues binded with this excahnge
DIRECT		| It will exactly match the routing key with the binding key and sends message to those queue only.
TOPIC		| It will PARTIALLY matches the routing key with the binding key and sends message to those queue only.
HEADER		| It will PARTIALLY matches the routing key with the binding key and sends message to those queues.
DEFAULT		| It will exactly match the routing key with the QUEUE name instead of binding key and sends message to those queue only.

In case of TOPIC
* => can be subsitute for exactly one word
# => can be subsitute for more than one word

routing key=task.iclear.app   
	==> binding Q1= *.app1.*
	==> binding Q2= *.app2.*

above example will pass queu to Q1 only	

Important** JMS vs AMQP
https://www.linkedin.com/pulse/jms-vs-amqp-eran-shaham/


****************************************To Run the project****************************
download rabbitmq officil docker image and run using
  docker run -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3-management
run springboot project to publish and consume message on rabbitMQ

