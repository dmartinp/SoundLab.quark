FasdUAS 1.101.10   ��   ��    k             l     ��  ��      run from command line     � 	 	 ,   r u n   f r o m   c o m m a n d   l i n e   
  
 l     ��  ��    K E provide mic number and phatom value (boolean) as command line, e.g.      �   �   p r o v i d e   m i c   n u m b e r   a n d   p h a t o m   v a l u e   ( b o o l e a n )   a s   c o m m a n d   l i n e ,   e . g .        l     ��  ��    . ( osascript ff800_setPhantom.scpt 8 false     �   P   o s a s c r i p t   f f 8 0 0 _ s e t P h a n t o m . s c p t   8   f a l s e      l     ��  ��    "  (turns on phantom on mic 8)     �   8   ( t u r n s   o n   p h a n t o m   o n   m i c   8 )      l     ��������  ��  ��     ��  i         I     �� ��
�� .aevtoappnull  �   � ****  o      ���� 0 argv  ��    O     I     O    H ! " ! k    G # #  $ % $ l   �� & '��   &   set frontmost to true    ' � ( ( ,   s e t   f r o n t m o s t   t o   t r u e %  ) * ) r     + , + n     - . - 4    �� /
�� 
cobj / m    ����  . o    ���� 0 argv   , o      ���� 0 micnum micNum *  0 1 0 r    & 2 3 2 n    $ 4 5 4 4    $�� 6
�� 
chbx 6 l    # 7���� 7 b     # 8 9 8 m     ! : : � ; ;  M i c   9 o   ! "���� 0 micnum micNum��  ��   5 n     < = < 4    �� >
�� 
sgrp > m     ? ? � @ @  P h a n t o m   P o w e r = n     A B A 4    �� C
�� 
sgrp C m    ����  B n     D E D 4    �� F
�� 
sgrp F m    ����  E 4    �� G
�� 
cwin G m     H H � I I " F i r e f a c e   S e t t i n g s 3 o      ���� "0 phantomcheckbox phantomCheckbox 1  J K J r   ' / L M L c   ' - N O N n   ' + P Q P 4   ( +�� R
�� 
cobj R m   ) *����  Q o   ' (���� 0 argv   O m   + ,��
�� 
bool M o      ���� 0 val   K  S T S r   0 7 U V U c   0 5 W X W n   0 3 Y Z Y 1   1 3��
�� 
valL Z o   0 1���� "0 phantomcheckbox phantomCheckbox X m   3 4��
�� 
bool V o      ����  0 checkboxstatus checkboxStatus T  [�� [ Z  8 G \ ]���� \ >  8 ; ^ _ ^ o   8 9����  0 checkboxstatus checkboxStatus _ o   9 :���� 0 val   ] I  > C�� `��
�� .prcsclicnull��� ��� uiel ` o   > ?���� "0 phantomcheckbox phantomCheckbox��  ��  ��  ��   " 4    �� a
�� 
prcs a m     b b � c c " F i r e f a c e   S e t t i n g s   m      d d�                                                                                  sevs  alis    P  system                         BD ����System Events.app                                              ����            ����  
 cu             CoreServices  0/:System:Library:CoreServices:System Events.app/  $  S y s t e m   E v e n t s . a p p    s y s t e m  -System/Library/CoreServices/System Events.app   / ��  ��       
�� e f g h������������   e ����������������
�� .aevtoappnull  �   � ****�� 0 micnum micNum�� "0 phantomcheckbox phantomCheckbox�� 0 val  ��  0 checkboxstatus checkboxStatus��  ��  ��   f �� ���� i j��
�� .aevtoappnull  �   � ****�� 0 argv  ��   i ���� 0 argv   j  d�� b������ H�� ?�� :������������
�� 
prcs
�� 
cobj�� 0 micnum micNum
�� 
cwin
�� 
sgrp
�� 
chbx�� "0 phantomcheckbox phantomCheckbox
�� 
bool�� 0 val  
�� 
valL��  0 checkboxstatus checkboxStatus
�� .prcsclicnull��� ��� uiel�� J� F*��/ >��k/E�O*��/�k/�k/��/���%/E�O��l/�&E�O��,�&E�O�� 
�j Y hUU g � k k  9 h  l l  m�� n m  o�� p o  q���� q  r���� r  s�� t s  d�� u
�� 
pcap u � v v " F i r e f a c e   S e t t i n g s
�� 
cwin t � w w " F i r e f a c e   S e t t i n g s
�� 
sgrp�� 
�� 
sgrp�� 
�� 
sgrp p � x x  P h a n t o m   P o w e r
�� 
chbx n � y y 
 M i c   9
�� boovfals
�� boovtrue��  ��  ��  ascr  ��ޭ