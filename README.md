# VehicleRoutingProblem
 Servis Rota Sistemi Mobil Uygulaması
 
Kocaeli’nin ilçelerinden Kocaeli Universitesi’ne gelen servis araçları için yolcu ve rota planlaması yapılmaktadır. Belirli araç ve durak sayısı bilgilerinin,duraklardaki yolcu sayısı bilgisi ile birleştirilerek en optimum rotaların oluşturulması hedeflenmektedir. Bunu yaparken araçların kiralama maliyeti ve yakıt tüketiminin de hesaplanarak yol-maliyet optimizasyonu da yapılmıştır.

Kullanıcı panelinde bineceği durak seçilir,Yönetim panelinde yeni duraklar eklenir binecek yolcu sayısı ve durak bilgileriyle rota planlaması olusturulur
Rota planlaması yapıldıktan sonra kullanıcılara bineceği aracın rotası gösterilir. \
Tüm aracın rotası admin panelinde gözükür.
Kullanıcılardan gelen talepler dogrulutusunda yönetici binecek yolcu sayısını belirler yönetici de yolcu ve bilgileri girebilir. \
Sınırsız sayıda servis aracı problemi: Minimum maliyetle kac servis aracı ile sefer tamamlanabilir? \
Araçların sayısı yeterli gelmediginde veya yol maliyeti arac kiralama maliyetini gectiginde belirli sayıda araç sistem tarafından kiralanabilmektedir. \
Belirli sayıda servis aracı:Minimum maliyet maksimum yolcu ile servis araçların guzergahları nasıl olmalıdır? \
Minimum maliyet maksimum yolcu ile hangi yolcuları kabul etmelidir? \
Yol maliyeti km başına 1 birim olarak kabul edilmelidir. \
Başlangıçta 3 servis aracı bulunmaktadır. Bu araçların kiralama maliyeti yoktur. \
Bu projede geliştirdiğimiz uygulamada, okul servislerinin; öğrencilerin talepleri ve her bir duraktaki yolcu sayısına göre, yol hesabı ve araç kiralama maliyeti hesaplanarak toplam maliyete göre en kısa en optimize rotalar bulma amaçlanmıştır.

Proje Java dilinde mobil uygulama olarak gerçekleştirilmiştir. \
Optimizasyon algoritması için Python dili kullanılmıştır. \
Harita arayüzü için Google Maps Api, Mesafe hesaplamaları ve yolları çizmek için Google Directions Api kullanılmıştır. \
Veri tabanı olarak Firebase kullanılmıştır.

  <img
  src="/images/1.png"
  alt="Alt text"
  title="Optional title"
  style="display: inline-block; margin: 0 auto; width: 300px"> 
  
  -Eğer admin girişi yaparsak karşımıza admin paneli çıkacaktır. Admin panelinde ilk olarak karşımıza durak adlarımız ve bu duraklara gelen talep sayısı belirlenmiştir.
  
  
  <img
  src="/images/2.png"
  alt="Alt text"
  title="Optional title"
  style="display: inline-block; margin: 0 auto; width: 300px"> 
  
  -Daha sonra üst çubukta yer alan düğmeye basınca slider menümüz açılır ve menüde seçimler yapılabilir.
  
  
  <img
  src="/images/3.png"
  alt="Alt text"
  title="Optional title"
  style="display: inline-block; margin: 0 auto; width: 300px"> 
  
  -Durak ekle kısmından istediğimiz konumu yeni bir durak olarak ekleyebiliriz. \
  -Default olarak tanımlı maliyet ve servis kapatilerini admin olarak değiştirebiliriz.Yolcu ekle panelimizden durak seçilerek duraklarımızda kaç yolcunun bekleyeceği yazılır.

  
  
  <img
  src="/images/4.png"
  alt="Alt text"
  title="Optional title"
  style="display: inline-block; margin: 0 auto; width: 300px"> 
  
  -Bu bilgiler veritabanına kaydedilir. Ve sen sonunda pythonda yazılmış algoritmamıza istek göndeririz ve çalışmaya başlar. Rota oluştur dediğimizde ise bize maliyeti düşük olan en kısa yolların rotalarını haritada gösterir.
  
  
  <img
  src="/images/5.png"
  alt="Alt text"
  title="Optional title"
  style="display: inline-block; margin: 0 auto; width: 300px"> 
  
  
