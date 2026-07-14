package kodlamaio.HRMS.config;

import kodlamaio.HRMS.entities.concretes.*;
import kodlamaio.HRMS.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final RoleDao roleDao;
    private final CityDao cityDao;
    private final JobTitleDao jobTitleDao;
    private final TypeOfWorkDao typeOfWorkDao;
    private final EmployerDao employerDao;
    private final JobSeekerDao jobSeekerDao;
    private final JobAdvertisementDao jobAdvertisementDao;
    private final JobApplicationDao jobApplicationDao;
    private final PasswordEncoder passwordEncoder;
    private final UserDao userDao;
    private final CategoryDao categoryDao;

    @Override
    public void run(String... args) throws Exception {
        // Seed Roles
        if (roleDao.findByRoleName(Role.JOBSEEKER).isEmpty()) {
            roleDao.save(new Role(null, Role.JOBSEEKER));
        }
        if (roleDao.findByRoleName(Role.EMPLOYER).isEmpty()) {
            roleDao.save(new Role(null, Role.EMPLOYER));
        }

        // Seed Cities
        if (cityDao.count() == 0) {
            List.of("Istanbul", "Ankara", "Izmir", "Bursa", "Antalya")
                .forEach(name -> cityDao.save(new City(null, name, null)));
        }

        // Seed Job Titles
        if (jobTitleDao.count() == 0) {
            List.of("Software Engineer", "Frontend Developer", "Backend Developer", "Product Manager", "UI/UX Designer")
                .forEach(title -> jobTitleDao.save(new JobTitle(null, title, null, null)));
        }

        // Seed Types of Work
        if (typeOfWorkDao.count() == 0) {
            List.of("Full-Time", "Part-Time", "Remote", "Freelance", "Internship")
                .forEach(name -> typeOfWorkDao.save(new TypeOfWork(null, name)));
        }

        // Seed Categories
        List.of("IT / Yazılım", "Pazarlama", "Finans", "Tasarım", "Satış", "İnsan Kaynakları", "Operasyon")
            .forEach(name -> {
                if (!categoryDao.existsByName(name)) {
                    categoryDao.save(new Category(null, name, null));
                }
            });

        // Seed Sample Data for JobAdvertisements and Applications if possible
        seedRealisticData();
    }

    private void seedRealisticData() {
        if (employerDao.count() > 15) return;

        Role employerRole = roleDao.findByRoleName(Role.EMPLOYER).orElse(null);
        if (employerRole == null) return;

        var cities = cityDao.findAll();
        var types = typeOfWorkDao.findAll();
        
        if (cities.isEmpty() || types.isEmpty()) return;

        // Helper to get category safely
        java.util.function.Function<String, Category> getCat = (name) -> 
            categoryDao.findByName(name).orElseGet(() -> categoryDao.save(new Category(null, name, null)));

        // --- COMPANY 1: NexTech Yazılım ---
        Employer nexTech = createEmployer("info@nextech.com", "NexTech Yazılım", "www.nextech.com", "2125550001", employerRole);
        Category itCategory = getCat.apply("IT / Yazılım");
        
        JobTitle backendTitle = getOrCreateJobTitle("Senior Backend Developer (Java)", itCategory);
        JobTitle cloudTitle = getOrCreateJobTitle("Cloud Solutions Architect", itCategory);

        createJobAd(nexTech, backendTitle, cities.get(0), types.get(0), 
            "1. Giriş\nNexTech Yazılım, bulut tabanlı kurumsal çözümler ve yapay zeka entegrasyonları konusunda Türkiye'nin öncü teknoloji şirketlerinden biridir. Finansal teknolojilerden e-ticarete kadar geniş bir yelpazede yüksek trafikli sistemler geliştiriyoruz. Bu rol, şirketimizin mikroservis mimarisini bir üst seviyeye taşıyacak ve milyonlarca kullanıcıya hizmet veren platformlarımızın temelini güçlendirecektir.\n\n" +
            "2. Rol Tanımı\nSenior Backend Developer olarak, karmaşık iş gereksinimlerini ölçeklenebilir ve sürdürülebilir teknik çözümlere dönüştürmekten sorumlu olacaksınız. Sadece kod yazmakla kalmayıp, sistem mimarisinin tasarım süreçlerinde aktif rol alacak ve junior geliştiricilere mentorluk yapacaksınız. Teknik ekipler ile iş birimleri arasında köprü kurarak, ürün vizyonumuzun hayata geçirilmesinde kritik bir stratejik öneme sahip olacaksınız.\n\n" +
            "3. Sorumluluklar\n- Yüksek performanslı, ölçeklenebilir mikroservis mimarileri tasarlamak ve geliştirmek.\n- Kod kalitesini artırmak için düzenli code review süreçlerini yönetmek ve best practice'leri belirlemek.\n- Veritabanı optimizasyonu ve sorgu performans iyileştirmeleri yaparak sistem yanıt sürelerini minimize etmek.\n- CI/CD süreçlerinin otomasyonuna katkıda bulunmak ve sistemlerin kesintisiz çalışmasını sağlamak.\n\n" +
            "4. Gereksinimler\n- Bilgisayar Mühendisliği veya ilgili bölümlerden lisans derecesi.\n- Java ve Spring Boot ekosisteminde en az 5 yıl profesyonel deneyim.\n- PostgreSQL, Redis ve Kafka gibi teknolojilerde ileri düzeyde yetkinlik.\n- Mikroservis mimarisi, Docker ve Kubernetes konularında tecrübe.\n- Karmaşık algoritmalar ve veri yapıları konusunda derin bilgi sahibi olmak.\n\n" +
            "5. Neden Bu Şirket?\nNexTech'te hiyerarşiden uzak, inovasyon odaklı bir çalışma ortamı sunuyoruz. En yeni teknolojileri gerçek dünya problemlerini çözmek için kullanma fırsatı bulacak, sürekli öğrenme kültürümüzün bir parçası olacaksınız. Çalışanlarımızın hem profesyonel hem de kişisel gelişimine yatırım yapıyor, esnek çalışma modelleriyle dengeli bir yaşamı destekliyoruz.\n\n" +
            "6. İşe Alım Süreci\nBaşvuru → Ön Değerlendirme → Teknik Mülakat (Live Coding) → Mimari Değerlendirme → HR Görüşmesi → Teklif");

        createJobAd(nexTech, cloudTitle, cities.get(1), types.get(2), 
            "1. Giriş\nNexTech bünyesinde, küresel ölçekteki müşterilerimizin dijital dönüşüm yolculuklarına rehberlik edecek bir Cloud Solutions Architect arıyoruz. Cloud-native yaklaşımlarımızla sektördeki standartları belirliyor, karmaşık altyapı sorunlarını modern teknolojilerle çözüyoruz.\n\n" +
            "2. Rol Tanımı\nBu rolde, müşterilerimizin iş ihtiyaçlarını analiz ederek AWS, Azure veya Google Cloud üzerinde güvenli, maliyet etkin ve yüksek erişilebilir mimariler tasarlayacaksınız. Satış öncesi süreçlerden canlıya geçiş sonrasına kadar tüm teknik stratejiyi siz kurgulayacaksınız.\n\n" +
            "3. Sorumluluklar\n- Kurumsal müşteriler için uçtan uca cloud mimarisi tasarlamak.\n- On-premise sistemlerin bulut ortamına taşınma (migration) stratejilerini belirlemek.\n- Infrastructure as Code (Terraform, CloudFormation) prensiplerini uygulamak.\n- Güvenlik ve uyumluluk standartlarına uygun altyapı denetimleri gerçekleştirmek.\n\n" +
            "4. Gereksinimler\n- Bulut teknolojileri üzerine en az 7 yıl deneyim ve ilgili sertifikalar.\n- Dağıtık sistemler ve network güvenliği konusunda uzmanlık.\n- DevOps araçları ve konteyner teknolojilerine hakimiyet.\n- Stratejik düşünme ve sunum yeteneği.\n\n" +
            "5. Neden Bu Şirket?\nBüyük ölçekli projelerde sorumluluk alma şansı, küresel sertifikasyon destekleri ve teknoloji dünyasının kalbinde yer alma fırsatı.\n\n" +
            "6. İşe Alım Süreci\nBaşvuru → Teknik Case Study → Panel Mülakat → Final Görüşme → Teklif");

        // --- COMPANY 2: Lumina Dijital ---
        Employer lumina = createEmployer("hr@lumina.digital", "Lumina Dijital", "www.lumina.digital", "2125550002", employerRole);
        Category marketingCategory = getCat.apply("Pazarlama");
        
        JobTitle digitalMarketingTitle = getOrCreateJobTitle("Digital Marketing Manager", marketingCategory);
        JobTitle performanceTitle = getOrCreateJobTitle("Performance Marketing Specialist", marketingCategory);

        createJobAd(lumina, digitalMarketingTitle, cities.get(0), types.get(0), 
            "1. Giriş\nLumina Dijital, veri analitiğini yaratıcılıkla birleştiren, markaların dijital büyüme yolculuklarında stratejik ortaklık yapan bir performans ajansıdır. Portföyümüzde yer alan global markaların dijital varlığını yönetecek vizyoner bir lider arıyoruz.\n\n" +
            "2. Rol Tanımı\nDigital Marketing Manager olarak, 360 derece dijital pazarlama stratejilerini kurgulayacak, bütçe yönetiminden ve kampanya performanslarından sorumlu olacaksınız. Ajans içi ekipler ile müşteri arasındaki koordinasyonu sağlayarak markalarımızın büyüme hedeflerine ulaşmasını koordine edeceksiniz.\n\n" +
            "3. Sorumluluklar\n- Çok kanallı pazarlama stratejileri geliştirmek (SEO, SEM, Social Media, Programmatic).\n- Pazarlama bütçelerinin ROI odaklı optimizasyonunu sağlamak.\n- Veri analitiği araçlarını kullanarak kampanya çıktılarını raporlamak ve iyileştirme planları sunmak.\n- Rakip analizi ve pazar trendlerini takip ederek stratejik yol haritaları oluşturmak.\n\n" +
            "4. Gereksinimler\n- Pazarlama veya İşletme mezunu, en az 6 yıl ajans deneyimi.\n- Google Ads, Facebook Business Manager ve GA4 konularında ileri düzey yetkinlik.\n- Ekip yönetimi ve müşteri ilişkileri becerisi.\n- İleri seviye İngilizce.\n\n" +
            "5. Neden Bu Şirket?\nGlobal markalarla çalışma fırsatı, veri odaklı bir ekosistem ve yaratıcı özgürlük tanıyan modern bir ajans kültürü.\n\n" +
            "6. İşe Alım Süreci\nBaşvuru → Portfolyo Sunumu → Stratejik Vaka Analizi → Yönetici Mülakatı → Teklif");

        createJobAd(lumina, performanceTitle, cities.get(2), types.get(2), 
            "1. Giriş\nVerinin gücüne inanan, rakamlarla konuşmayı seven ve dijital reklam platformlarının tüm detaylarına hakim bir Performance Marketing Specialist arıyoruz.\n\n" +
            "2. Rol Tanımı\nBu rolde, markalarımızın satış ve dönüşüm hedeflerini gerçekleştirmek için reklam kampanyalarını teknik olarak kurgulayacak, A/B testleri yürütecek ve her bir kuruşun en verimli şekilde harcandığından emin olacaksınız.\n\n" +
            "3. Sorumluluklar\n- Google, Meta, TikTok ve LinkedIn reklam panellerinde kampanya kurulumları yapmak.\n- Conversion tracking ve event kurulumlarını yönetmek (GTM).\n- Günlük bütçe takibi ve teklif stratejisi optimizasyonları.\n- Kreatif performanslarını analiz ederek içerik ekibini yönlendirmek.\n\n" +
            "4. Gereksinimler\n- Sayısal bilimlerden mezun, en az 3 yıl aktif kampanya yönetimi deneyimi.\n- SQL veya Python (veri analizi için) bilgisi tercih sebebidir.\n- Analitik düşünme ve detaycılık.\n\n" +
            "5. Neden Bu Şirket?\nSürekli öğrenmeyi destekleyen bütçeler, en yeni reklam teknolojilerine erken erişim ve esnek çalışma imkanı.\n\n" +
            "6. İşe Alım Süreci\nBaşvuru → Teknik Sınav → Case Study → Ekip Tanışması → Teklif");

        // --- COMPANY 3: Vera Finans ---
        Employer vera = createEmployer("careers@verafinans.com", "Vera Finansal Danışmanlık", "www.verafinans.com", "2125550003", employerRole);
        Category financeCategory = getCat.apply("Finans");
        
        JobTitle analystTitle = getOrCreateJobTitle("Senior Financial Analyst", financeCategory);
        JobTitle riskTitle = getOrCreateJobTitle("Risk Management Specialist", financeCategory);

        createJobAd(vera, analystTitle, cities.get(0), types.get(0), 
            "1. Giriş\nVera Finans, kurumsal şirketlere ve bireysel yatırımcılara yüksek katma değerli finansal stratejiler sunan bir butik danışmanlık firmasıdır. Finans dünyasının karmaşıklığını sadeleştirerek müşterilerimize rehberlik ediyoruz.\n\n" +
            "2. Rol Tanımı\nSenior Financial Analyst olarak, piyasa verilerini analiz edecek, finansal modeller kurgulayacak ve yatırım kararlarına baz teşkil edecek raporlar hazırlayacaksınız. Stratejik karar alma süreçlerinde yönetim kuruluna doğrudan destek vereceksiniz.\n\n" +
            "3. Sorumluluklar\n- Şirket değerleme ve fizibilite çalışmaları yürütmek.\n- Makroekonomik trendleri takip ederek sektörel analizler hazırlamak.\n- Bütçe planlama ve varyans analizleri gerçekleştirmek.\n- Yatırımcı sunumları ve finansal dashboardlar oluşturmak.\n\n" +
            "4. Gereksinimler\n- İktisat, İşletme veya Finans mezunu.\n- CFA sertifikası veya adaylığı tercih sebebidir.\n- İleri düzey Excel ve finansal modelleme yetkinliği.\n- En az 5 yıl kurumsal finans deneyimi.\n\n" +
            "5. Neden Bu Şirket?\nFinans dünyasının en iyileriyle çalışma fırsatı, stratejik projelerde insiyatif alma ve performans odaklı yüksek prim sistemi.\n\n" +
            "6. İşe Alım Süreci\nBaşvuru → Sayısal Yetenek Testi → Teknik Vaka Çözümü → Partner Görüşmesi → Teklif");

        createJobAd(vera, riskTitle, cities.get(1), types.get(0), 
            "1. Giriş\nFinansal piyasaların belirsizliklerini fırsata çevirmek için riskleri doğru yönetmek gerekir. Vera Finans bünyesinde bu dengeyi kuracak bir uzman arıyoruz.\n\n" +
            "2. Rol Tanımı\nRisk Management Specialist olarak, kredi riski, piyasa riski ve operasyonel risklerin izlenmesi ve minimize edilmesi süreçlerini yöneteceksiniz. Şirketin risk iştahına uygun politikaların geliştirilmesinde aktif rol alacaksınız.\n\n" +
            "3. Sorumluluklar\n- Risk ölçüm modelleri (VaR vb.) geliştirmek ve çalıştırmak.\n- Limit aşımlarını takip etmek ve raporlamak.\n- Stress testleri ve senaryo analizleri yapmak.\n- İç denetim süreçlerine teknik destek sağlamak.\n\n" +
            "4. Gereksinimler\n- İstatistik, Matematik veya Ekonometri mezunu.\n- Risk yönetimi yazılımlarına hakimiyet.\n- Güçlü analitik zeka ve raporlama becerisi.\n\n" +
            "5. Neden Bu Şirket?\nKurumsal disiplin ile butik hizmet anlayışının birleştiği, profesyonel gelişime açık bir çalışma ortamı.\n\n" +
            "6. İşe Alım Süreci\nBaşvuru → Teknik Mülakat → Simülasyon → Final Görüşme → Teklif");

        // --- COMPANY 4: Artis Stüdyo ---
        Employer artis = createEmployer("hello@artis.studio", "Artis Tasarım Stüdyosu", "www.artis.studio", "2125550004", employerRole);
        Category designCategory = getCat.apply("Tasarım");
        
        JobTitle uiuxTitle = getOrCreateJobTitle("Senior UI/UX Designer", designCategory);
        JobTitle productDesignTitle = getOrCreateJobTitle("Product Designer", designCategory);

        createJobAd(artis, uiuxTitle, cities.get(0), types.get(2), 
            "1. Giriş\nArtis Stüdyo, dijital dünyada estetik ile fonksiyonelliği birleştiren ödüllü bir tasarım ajansıdır. Sadece güzel görünen değil, gerçekten çalışan deneyimler tasarlıyoruz.\n\n" +
            "2. Rol Tanımı\nSenior UI/UX Designer olarak, karmaşık dijital platformların kullanıcı deneyimini baştan sona tasarlayacaksınız. Kullanıcı araştırmalarından prototiplemeye, tasarım sistemlerinden high-fidelity UI tasarımlarına kadar tüm süreçlere liderlik edeceksiniz.\n\n" +
            "3. Sorumluluklar\n- Kullanıcı senaryoları, wireframe ve prototipler oluşturmak.\n- Kapsamlı ve ölçeklenebilir tasarım sistemleri (Design Systems) kurmak.\n- Kullanıcı testleri yaparak tasarım kararlarını veriyle desteklemek.\n- Geliştirme ekibiyle koordineli çalışarak tasarımların doğruluğunu denetlemek.\n\n" +
            "4. Gereksinimler\n- Güçlü bir portfolyo (Behance, Dribbble vb.).\n- Figma, Adobe Creative Suite ve Prototipleme araçlarında uzmanlık.\n- Kullanıcı odaklı tasarım prensiplerine (Design Thinking) derin hakimiyet.\n- En az 6 yıl dijital ürün tasarım deneyimi.\n\n" +
            "5. Neden Bu Şirket?\nYaratıcılığın kutsandığı, 'pixel-perfect' tutkusunun paylaşıldığı ve sadece en iyi projelerle uğraşılan bir vizyon.\n\n" +
            "6. İşe Alım Süreci\nBaşvuru → Portfolyo İncelemesi → Tasarım Challenge → Case Sunumu → Teklif");

        createJobAd(artis, productDesignTitle, cities.get(2), types.get(0), 
            "1. Giriş\nBir ürünün sadece arayüzünü değil, ruhunu tasarlayacak bir Product Designer arıyoruz. Artis bünyesinde yeni nesil girişimlere yön verebilirsiniz.\n\n" +
            "2. Rol Tanımı\nSiz sadece bir tasarımcı değil, aynı zamanda bir problem çözücüsünüz. Ürün vizyonunu anlayıp kullanıcıların hayatını kolaylaştıracak yenilikçi özellikler geliştirecek, stratejik bir bakış açısıyla ürünü büyüteceksiniz.\n\n" +
            "3. Sorumluluklar\n- Ürün yol haritasına göre yeni özellik tasarımları yapmak.\n- İş birimleri ile ortak atölyeler düzenleyerek fikirleri somutlaştırmak.\n- Micro-interactions ve animasyonlarla deneyimi zenginleştirmek.\n\n" +
            "4. Gereksinimler\n- Dijital ürün ekosistemine hakimiyet.\n- Meraklı, öğrenmeye açık ve estetik algısı yüksek.\n- Agile çalışma disiplinine uyum.\n\n" +
            "5. Neden Bu Şirket?\nÖzgür çalışma saatleri, eğitim bütçesi ve her hafta düzenlenen tasarım ilham seansları.\n\n" +
            "6. İşe Alım Süreci\nBaşvuru → İlk Görüşme → Küçük Bir Ödev → Ekip Mülakatı → Teklif");

        // --- COMPANY 5: Global Satış A.Ş. ---
        Employer globalSatis = createEmployer("talent@globalsatis.com", "Global Satış ve Lojistik", "www.globalsatis.com", "2125550005", employerRole);
        Category salesCategory = getCat.apply("Satış");
        
        JobTitle accountManagerTitle = getOrCreateJobTitle("Key Account Manager", salesCategory);
        JobTitle salesOpsTitle = getOrCreateJobTitle("Sales Operations Lead", salesCategory);

        createJobAd(globalSatis, accountManagerTitle, cities.get(0), types.get(0), 
            "1. Giriş\nGlobal Satış, uluslararası pazarlarda markaların büyümesini sağlayan dev bir satış ve lojistik ağına sahiptir. En değerli müşterilerimizi (Key Accounts) yönetecek profesyoneller arıyoruz.\n\n" +
            "2. Rol Tanımı\nKey Account Manager olarak, mevcut büyük müşterilerle ilişkileri derinleştirmek, yeni iş fırsatları yaratmak ve uzun vadeli stratejik iş ortaklıklarını yönetmekten sorumlu olacaksınız. Satış hedeflerine ulaşırken müşteri memnuniyetini en üst seviyede tutacaksınız.\n\n" +
            "3. Sorumluluklar\n- Portföydeki müşteriler için yıllık satış planları hazırlamak.\n- Sözleşme müzakerelerini yönetmek ve sonuçlandırmak.\n- Müşteri ihtiyaçlarını analiz ederek özelleştirilmiş çözümler sunmak.\n- Satış sonrası süreçlerin kalitesini denetlemek.\n\n" +
            "4. Gereksinimler\n- Satış alanında en az 8 yıl deneyim, özellikle B2B tecrübesi.\n- İkna kabiliyeti ve müzakere yeteneği yüksek.\n- Analitik düşünme ve CRM araçlarına hakimiyet.\n- Sık seyahat engeli bulunmamak.\n\n" +
            "5. Neden Bu Şirket?\nGlobal bir kariyer yolu, geniş yan haklar ve sektördeki en güçlü satış ekibinin parçası olma imkanı.\n\n" +
            "6. İşe Alım Süreci\nBaşvuru → IK Mülakatı → Satış Direktörü Görüşmesi → Rol Yapma (Role Play) → Teklif");

        createJobAd(globalSatis, salesOpsTitle, cities.get(1), types.get(0), 
            "1. Giriş\nSatış ekibinin başarısı, arkasındaki güçlü operasyona bağlıdır. Global Satış bünyesinde satış süreçlerini mükemmelleştirecek bir lider arıyoruz.\n\n" +
            "2. Rol Tanımı\nSales Operations Lead olarak, satış verimliliğini artırmak için süreçleri optimize edecek, veri analizleri yapacak ve satış ekibinin performansını takip edecek sistemleri yöneteceksiniz. Teknoloji ve veriyi satışın merkezine koyacaksınız.\n\n" +
            "3. Sorumluluklar\n- Satış tahmin modelleri (Forecasting) oluşturmak.\n- Satış ekipleri için prim sistemleri ve hedef dağılımını kurgulamak.\n- CRM sisteminin (Salesforce vb.) yönetimini ve geliştirilmesini sağlamak.\n- Satış eğitim programlarını organize etmek.\n\n" +
            "4. Gereksinimler\n- Endüstri Mühendisliği veya İşletme mezunu.\n- Veri analizi ve süreç iyileştirme konusunda uzmanlık.\n- SQL ve BI araçları bilgisi.\n\n" +
            "5. Neden Bu Şirket?\nStratejik karar alma süreçlerinde doğrudan etki, dinamik bir çalışma temposu ve sürekli gelişim.\n\n" +
            "6. İşe Alım Süreci\nBaşvuru → Analitik Sınav → Case Study → Yönetici Mülakatı → Teklif");

        // --- COMPANY 6: İnsan Odaklı İK ---
        Employer insanOdakli = createEmployer("kariyer@insanodakli.com", "İnsan Odaklı İK ve Danışmanlık", "www.insanodakli.com", "2125550006", employerRole);
        Category hrCategory = getCat.apply("İnsan Kaynakları");
        
        JobTitle hrbpTitle = getOrCreateJobTitle("HR Business Partner", hrCategory);
        JobTitle talentAcquisitionTitle = getOrCreateJobTitle("Senior Talent Acquisition Specialist", hrCategory);

        createJobAd(insanOdakli, hrbpTitle, cities.get(0), types.get(0), 
            "1. Giriş\nİnsan Odaklı İK, modern çalışma kültürlerini inşa eden bir danışmanlık firmasıdır. Sadece 'personel' değil, 'insan' kıymetine odaklanıyoruz.\n\n" +
            "2. Rol Tanımı\nHR Business Partner olarak, atandığınız iş birimlerinin stratejik ortağı olacaksınız. Organizasyonel gelişimden çalışan bağlılığına, performans yönetiminden yetenek planlamasına kadar tüm İK süreçlerini iş birimi hedefleriyle uyumlu hale getireceksiniz.\n\n" +
            "3. Sorumluluklar\n- İş birimi yöneticilerine yönetimsel ve stratejik İK danışmanlığı yapmak.\n- Kültürel değişim ve dönüşüm projelerini yürütmek.\n- Yetenek yönetimi ve yedekleme planlarını oluşturmak.\n- Çalışan deneyimini iyileştirecek aksiyonlar almak.\n\n" +
            "4. Gereksinimler\n- İK alanında en az 7 yıl deneyim.\n- Modern İK trendlerine ve iş hukukuna hakimiyet.\n- Güçlü iletişim, empati ve koçluk becerileri.\n\n" +
            "5. Neden Bu Şirket?\nİnsan kaynakları vizyonunuzu hayata geçirebileceğiniz bir laboratuvar ortamı ve sürekli öğrenen bir organizasyon.\n\n" +
            "6. İşe Alım Süreci\nBaşvuru → Tanışma → Yetkinlik Mülakatı → Panel Görüşme → Teklif");

        createJobAd(insanOdakli, talentAcquisitionTitle, cities.get(0), types.get(2), 
            "1. Giriş\nDoğru yeteneği bulmak bir sanat, doğru yere yerleştirmek ise bir bilimdir. Bu dengeyi kuracak bir Talent Acquisition profesyoneli arıyoruz.\n\n" +
            "2. Rol Tanımı\nTeknoloji dünyasının en iyi yeteneklerini keşfedecek, aday deneyimini unutulmaz kılacak ve işveren markamızı güçlendireceksiniz. Headhunting yöntemlerinden sosyal medya stratejilerine kadar tüm araçları aktif kullanacaksınız.\n\n" +
            "3. Sorumluluklar\n- Uçtan uca işe alım süreçlerini yönetmek.\n- Sosyal ağlar üzerinden pasif aday havuzları oluşturmak.\n- Aday mülakat tekniklerini geliştirmek ve standartlaştırmak.\n- Üniversite etkinlikleri ve teknoloji zirvelerinde şirketi temsil etmek.\n\n" +
            "4. Gereksinimler\n- Özellikle teknoloji işe alımında uzmanlaşmış en az 5 yıl deneyim.\n- Employer Branding projelerine ilgi ve tecrübe.\n- Sonuç odaklılık ve yüksek enerji.\n\n" +
            "5. Neden Bu Şirket?\nYetenek avcılığında özgürlük, en modern ATS araçları ve global bir aday havuzu.\n\n" +
            "6. İşe Alım Süreci\nBaşvuru → İlk Mülakat → Simülasyon (Source-Hunt) → Final → Teklif");

        // --- COMPANY 7: Sinerji Enerji ---
        Employer sinerji = createEmployer("jobs@sinerjienerji.com", "Sinerji Enerji Sistemleri", "www.sinerjienerji.com", "2125550007", employerRole);
        Category opCategory = getCat.apply("Operasyon");
        
        JobTitle opsManagerTitle = getOrCreateJobTitle("Operations Manager", opCategory);
        JobTitle energyEngineerTitle = getOrCreateJobTitle("Energy Systems Engineer", opCategory);

        createJobAd(sinerji, opsManagerTitle, cities.get(0), types.get(0), 
            "1. Giriş\nSinerji Enerji, sürdürülebilir bir gelecek için yenilenebilir enerji kaynaklarını teknolojiyle birleştiriyor. Operasyonlarımızın kusursuz işlemesini sağlayacak bir lider arıyoruz.\n\n" +
            "2. Rol Tanımı\nOperations Manager olarak, tüm operasyonel süreçlerin verimliliğinden, maliyet kontrolünden ve kalite standartlarından sorumlu olacaksınız. Ekipler arası koordinasyonu sağlayarak projelerin zamanında ve bütçesinde tamamlanmasını koordine edeceksiniz.\n\n" +
            "3. Sorumluluklar\n- Operasyonel bütçeleri yönetmek ve tasarruf projeleri geliştirmek.\n- Tedarik zinciri ve lojistik süreçlerini optimize etmek.\n- İş sağlığı, güvenliği ve çevre standartlarına uyumu denetlemek.\n- KPI takibi ve periyodik performans raporlaması yapmak.\n\n" +
            "4. Gereksinimler\n- Mühendislik mezunu, operasyon yönetiminde en az 10 yıl deneyim.\n- Stratejik planlama ve kriz yönetimi becerisi.\n- Lean ve Six Sigma yeşil/siyah kuşak sertifikası tercih sebebidir.\n\n" +
            "5. Neden Bu Şirket?\nGeleceğin sektöründe kalıcı bir iz bırakma şansı ve büyük ölçekli bir operasyonu yönetme tatmini.\n\n" +
            "6. İşe Alım Süreci\nBaşvuru → Teknik Mülakat → Yönetim Kurulu Görüşmesi → Saha Ziyareti → Teklif");

        createJobAd(sinerji, energyEngineerTitle, cities.get(3), types.get(0), 
            "1. Giriş\nGüneş ve rüzgar enerjisi projelerimizde teknik mükemmelliği sağlayacak mühendisler arıyoruz.\n\n" +
            "2. Rol Tanımı\nEnergy Systems Engineer olarak, yenilenebilir enerji santrallerinin tasarımı, kurulumu ve verimlilik analizleri üzerinde çalışacaksınız. En yeni enerji depolama ve dağıtım teknolojilerini projelerimize entegre edeceksiniz.\n\n" +
            "3. Sorumluluklar\n- Santral projeleri için teknik çizim ve hesaplamalar yapmak.\n- Saha kurulum süreçlerini denetlemek.\n- Enerji verimliliği etütleri hazırlamak.\n- Ar-Ge projelerinde aktif görev almak.\n\n" +
            "4. Gereksinimler\n- Elektrik-Elektronik veya Enerji Mühendisliği mezunu.\n- Yenilenebilir enerji projelerinde en az 3 yıl saha deneyimi.\n- AutoCAD ve ilgili simülasyon yazılımlarına hakimiyet.\n\n" +
            "5. Neden Bu Şirket?\nYeşil enerji dönüşümünün mutfağında olma fırsatı ve teknik uzmanlık kazandıracak eğitimler.\n\n" +
            "6. İşe Alım Süreci\nBaşvuru → Teknik Sınav → Saha Mühendisi Mülakatı → Teklif");

        // --- COMPANY 8: Nova Market ---
        Employer nova = createEmployer("jobs@novamarket.com", "Nova E-Ticaret Platformu", "www.novamarket.com", "2125550008", employerRole);
        
        JobTitle categoryManagerTitle = getOrCreateJobTitle("E-commerce Category Manager", marketingCategory);
        JobTitle fullstackTitle = getOrCreateJobTitle("Senior Full Stack Developer", itCategory);

        createJobAd(nova, categoryManagerTitle, cities.get(0), types.get(0), 
            "1. Giriş\nNova Market, Türkiye'nin en hızlı büyüyen pazaryeri platformudur. Kendi kategorisinde pazar liderliğini hedefleyecek vizyoner yöneticiler arıyoruz.\n\n" +
            "2. Rol Tanımı\nCategory Manager olarak, sorumlu olduğunuz ürün grubunun satış performansından, tedarikçi ilişkilerinden ve pazarlama stratejilerinden tam sorumlu olacaksınız. Veriyi kullanarak doğru ürünü doğru fiyatla müşteriye ulaştıracaksınız.\n\n" +
            "3. Sorumluluklar\n- Kategori satış ve karlılık hedeflerini gerçekleştirmek.\n- Yeni tedarikçiler bulmak ve mevcut portföyü büyütmek.\n- Kampanya dönemlerini planlamak ve yönetmek.\n- Müşteri geri bildirimlerine göre ürün gamını optimize etmek.\n\n" +
            "4. Gereksinimler\n- E-ticaret sektöründe kategori yönetimi alanında en az 5 yıl deneyim.\n- Veri analizi ve ticari zeka.\n- Müzakere ve iletişim becerisi.\n\n" +
            "5. Neden Bu Şirket?\nDinamik, genç bir ekip ve Türkiye'nin dijital ticaretini şekillendirme şansı.\n\n" +
            "6. İşe Alım Süreci\nBaşvuru → Sayısal Test → Case Study → Kategori Direktörü Görüşmesi → Teklif");

        createJobAd(nova, fullstackTitle, cities.get(0), types.get(2), 
            "1. Giriş\nNova Market'in yüksek trafikli sistemlerini geliştirecek, hem front-end hem back-end dünyasına hakim 'ninja' geliştiriciler arıyoruz.\n\n" +
            "2. Rol Tanımı\nSenior Full Stack Developer olarak, kullanıcılarımızın her gün kullandığı özellikleri uçtan uca tasarlayıp geliştireceksiniz. React ve Java/Spring Boot dünyasında harikalar yaratırken, sistem mimarisine de katkı sağlayacaksınız.\n\n" +
            "3. Sorumluluklar\n- Mikro Front-end ve Mikroservis mimarileriyle çalışmak.\n- Responsive ve yüksek performanslı UI bileşenleri geliştirmek.\n- API tasarımı ve veri tabanı şemalarını kurgulamak.\n- Unit ve Integration testlerini yazarak kod güvenliğini sağlamak.\n\n" +
            "4. Gereksinimler\n- React.js ve Spring Boot üzerine en az 6 yıl profesyonel deneyim.\n- Modern web teknolojilerine (HTML5, CSS3, ES6+) derin hakimiyet.\n- Bulut ortamlarında (AWS/Azure) deployment süreçlerine aşinalık.\n\n" +
            "5. Neden Bu Şirket?\nTeknoloji borcu olmayan bir stack, sürekli yeni özellik geliştirme heyecanı ve yan haklar paketi.\n\n" +
            "6. İşe Alım Süreci\nBaşvuru → Teknik Case → Pair Programming → CTO Görüşmesi → Teklif");

        // --- COMPANY 9: Kreatif Ajans ---
        Employer kreatif = createEmployer("info@kreatifajans.com", "Kreatif Reklamcılık", "www.kreatifajans.com", "2125550009", employerRole);
        
        JobTitle creativeDirectorTitle = getOrCreateJobTitle("Creative Director", designCategory);
        JobTitle copywriterTitle = getOrCreateJobTitle("Senior Copywriter", marketingCategory);

        createJobAd(kreatif, creativeDirectorTitle, cities.get(0), types.get(0), 
            "1. Giriş\nKreatif Ajans'ta biz reklam değil, hikaye yazıyoruz. Markaların ruhunu keşfedecek bir yaratıcı lider arıyoruz.\n\n" +
            "2. Rol Tanımı\nCreative Director olarak, kreatif ekibe vizyon verecek, büyük kampanyaların konseptlerini kurgulayacak ve ajansın tasarım kalitesini en üst seviyede tutacaksınız. Müşterilere sunum yaparken onları hikayenize inandıracaksınız.\n\n" +
            "3. Sorumluluklar\n- 360 derece reklam kampanyalarının yaratıcı süreçlerini yönetmek.\n- Tasarım ve metin yazarı ekiplerine mentorluk yapmak.\n- Marka kimliği ve stratejik konumlandırma çalışmaları yürütmek.\n- Ajansın portfolyosunu ödüllü işlerle zenginleştirmek.\n\n" +
            "4. Gereksinimler\n- Ajans dünyasında en az 12 yıl deneyim ve geniş bir portfolyo.\n- Ödüllü projelere imza atmış olmak.\n- İnanılmaz bir estetik algısı ve liderlik vasfı.\n\n" +
            "5. Neden Bu Şirket?\nSınırların olmadığı bir yaratıcılık alanı ve Türkiye'nin en sevilen markalarıyla çalışma keyfi.\n\n" +
            "6. İşe Alım Süreci\nBaşvuru → Portfolyo Kritiği → Tanışma Kahvesi → Final Sunum → Teklif");

        createJobAd(kreatif, copywriterTitle, cities.get(0), types.get(2), 
            "1. Giriş\nKelimelerle dünyayı değiştirmeye hazır mısınız? Kreatif bünyesinde metinlere can verecek bir yazar arıyoruz.\n\n" +
            "2. Rol Tanımı\nSadece slogan değil, markanın sesini tasarlayacaksınız. Dijital içeriklerden TV senaryolarına kadar her alanda ikna edici, eğlenceli ve etkileyici metinler üreteceksiniz.\n\n" +
            "3. Sorumluluklar\n- Kampanya fikirleri ve sloganlar geliştirmek.\n- Sosyal medya içerik planlarını yazmak.\n- Blog, makale ve bülten metinleri hazırlamak.\n- Görsel ekiple uyumlu konseptler çıkarmak.\n\n" +
            "4. Gereksinimler\n- Türkçeye mükemmel hakimiyet, kelime oyunlarına merak.\n- En az 5 yıl metin yazarlığı tecrübesi.\n- Hızlı düşünen ve trendleri takip eden bir kafa yapısı.\n\n" +
            "5. Neden Bu Şirket?\nFikirlerinizin değer gördüğü, her gün yeni bir şey öğrendiğiniz ve çok eğlendiğimiz bir ofis.\n\n" +
            "6. İşe Alım Süreci\nBaşvuru → Metin Yazma Ödevi → Ekip Görüşmesi → Teklif");

        // --- COMPANY 10: Zirve Yatırım ---
        Employer zirve = createEmployer("careers@zirveyatirim.com", "Zirve Gayrimenkul Yatırım", "www.zirveyatirim.com", "2125550010", employerRole);
        
        JobTitle investmentConsultantTitle = getOrCreateJobTitle("Investment Consultant", financeCategory);
        JobTitle valuationExpertTitle = getOrCreateJobTitle("Real Estate Valuation Expert", financeCategory);

        createJobAd(zirve, investmentConsultantTitle, cities.get(0), types.get(0), 
            "1. Giriş\nZirve Yatırım, gayrimenkul dünyasında veriye dayalı yatırım modelleriyle sektöre yön veriyor. Portföyümüzü büyütecek danışmanlar arıyoruz.\n\n" +
            "2. Rol Tanımı\nInvestment Consultant olarak, kurumsal ve bireysel yatırımcılara gayrimenkul portföy yönetimi konusunda danışmanlık yapacaksınız. Pazar fırsatlarını analiz ederek karlı yatırım senaryoları geliştirecek ve satış kapama süreçlerini yöneteceksiniz.\n\n" +
            "3. Sorumluluklar\n- Yatırımcı ağı oluşturmak ve yönetmek.\n- Bölgesel gayrimenkul piyasası analizleri yapmak.\n- Yatırım geri dönüş (ROI) raporları hazırlamak.\n- Satış ve kiralama müzakerelerini yürütmek.\n\n" +
            "4. Gereksinimler\n- Satış veya Finans alanında en az 6 yıl deneyim.\n- Gayrimenkul sektörüne dair mevzuat bilgisi.\n- İkna kabiliyeti ve network kurma becerisi.\n\n" +
            "5. Neden Bu Şirket?\nYüksek kazanç potansiyeli, prestijli projeler ve kurumsal bir yatırım markasının gücü.\n\n" +
            "6. İşe Alım Süreci\nBaşvuru → Mülakat → Teknik Sunum (Pazar Analizi) → Teklif");

        createJobAd(zirve, valuationExpertTitle, cities.get(0), types.get(0), 
            "1. Giriş\nBir mülkün gerçek değerini veriler söyler. Zirve Yatırım'da bu verileri konuşturacak uzmanlar arıyoruz.\n\n" +
            "2. Rol Tanımı\nGayrimenkul Değerleme Uzmanı olarak, projelerimizin ve portföyümüzdeki mülklerin piyasa değerlerini bilimsel yöntemlerle belirleyeceksiniz. Risk analizleri yaparak yatırım güvenliğini sağlayacaksınız.\n\n" +
            "3. Sorumluluklar\n- Resmi değerleme raporları hazırlamak.\n- İmar durumu ve yasal süreçleri takip etmek.\n- Benzer satış analizleri (comparable analysis) yapmak.\n- Ekspertiz süreçlerini yönetmek.\n\n" +
            "4. Gereksinimler\n- SPK Gayrimenkul Değerleme Lisansı sahibi.\n- Alanında en az 4 yıl deneyim.\n- Teknik raporlama ve detaylara dikkat.\n\n" +
            "5. Neden Bu Şirket?\nProfesyonel yetkinliklerinizi sergileyebileceğiniz kurumsal bir yapı ve teknik gelişim destekleri.\n\n" +
            "6. İşe Alım Süreci\nBaşvuru → Teknik Sınav → Bölüm Müdürü Mülakatı → Teklif");

        // --- COMPANY 11: Aura Kozmetik ---
        Employer aura = createEmployer("hello@aurabeauty.com", "Aura Kozmetik ve Bakım", "www.aurabeauty.com", "2125550011", employerRole);
        JobTitle brandManagerTitle = getOrCreateJobTitle("Brand Manager (Marka Müdürü)", marketingCategory);
        JobTitle salesRepTitle = getOrCreateJobTitle("Retail Sales Representative", salesCategory);

        createJobAd(aura, brandManagerTitle, cities.get(0), types.get(0), 
            "1. Giriş\nAura Kozmetik, doğadan aldığı ilhamı modern bilimle birleştirerek premium kişisel bakım ürünleri geliştiren global bir vizyona sahip markadır. Markamızın hikayesini büyütecek bir lider arıyoruz.\n\n" +
            "2. Rol Tanımı\nBrand Manager olarak, Aura'nın pazardaki konumlandırmasını yönetecek, ürün lansman stratejilerini kurgulayacak ve marka bilinirliğini artıracak kreatif kampanyalara imza atacaksınız. Tüketici içgörülerini iş fırsatlarına dönüştüreceksiniz.\n\n" +
            "3. Sorumluluklar\n- Yıllık pazarlama planlarını ve bütçelerini yönetmek.\n- Yeni ürün geliştirme (NPD) süreçlerinde Ar-Ge ekibiyle koordineli çalışmak.\n- Medya planlama ve PR ajanslarını yönlendirmek.\n- Pazar payı ve marka sağlığı metriklerini takip etmek.\n\n" +
            "4. Gereksinimler\n- FMCG veya Kozmetik sektöründe en az 6 yıl marka yönetimi deneyimi.\n- Kreatif ajans yönetimi ve kampanya kurgulama tecrübesi.\n- Güçlü sunum ve iletişim yeteneği.\n\n" +
            "5. Neden Bu Şirket?\nEstetik ve kalitenin ön planda olduğu bir çalışma ortamı, global kariyer fırsatları ve sürdürülebilir bir güzellik anlayışı.\n\n" +
            "6. İşe Alım Süreci\nBaşvuru → Kreatif Vaka Analizi → Pazarlama Direktörü Mülakatı → Teklif");

        createJobAd(aura, salesRepTitle, cities.get(2), types.get(1), 
            "1. Giriş\nAura dünyasının perakende noktalarındaki elçisi olacak, müşterilerimize eşsiz bir deneyim sunacak çalışma arkadaşları arıyoruz.\n\n" +
            "2. Rol Tanımı\nMüşterilerimizin cilt tipi ve ihtiyaçlarına göre en doğru ürünleri önerecek, Aura'nın premium hizmet anlayışını mağazalarımızda yaşatacaksınız. Satış hedeflerini gerçekleştirirken sadık bir müşteri kitlesi oluşturacaksınız.\n\n" +
            "3. Sorumluluklar\n- Müşterilere ürün tanıtımı ve kişiselleştirilmiş bakım önerileri sunmak.\n- Mağaza görsel düzenini ve stok takibini yönetmek.\n- Sadakat programı üyeliklerini artırmak.\n\n" +
            "4. Gereksinimler\n- Kozmetik sektöründe satış tecrübesi olan.\n- Güler yüzlü, dinamik ve ikna kabiliyeti yüksek.\n- Kişisel bakım trendlerine ilgili.\n\n" +
            "5. Neden Bu Şirket?\nKapsamlı ürün eğitimleri, satış odaklı prim sistemi ve enerjik bir ekip ruhu.\n\n" +
            "6. İşe Alım Süreci\nBaşvuru → Mağaza Mülakatı → Deneme Günü → Teklif");

        // --- COMPANY 12: EkoYapı İnşaat ---
        Employer ekoyapi = createEmployer("info@ekoyapi.com", "EkoYapı İnşaat Teknolojileri", "www.ekoyapi.com", "2125550012", employerRole);
        JobTitle architectureTitle = getOrCreateJobTitle("Sustainable Architecture Specialist", designCategory);
        JobTitle civilEngineerTitle = getOrCreateJobTitle("Senior Civil Engineer", opCategory);

        createJobAd(ekoyapi, architectureTitle, cities.get(0), types.get(0), 
            "1. Giriş\nEkoYapı, sadece binalar değil, çevreyle uyumlu yaşam alanları inşa eden bir teknoloji ve mühendislik firmasıdır. Geleceğin şehirlerini tasarlamaya davetlisiniz.\n\n" +
            "2. Rol Tanımı\nSürdürülebilir Mimari Uzmanı olarak, projelerimizin karbon ayak izini minimize edecek, enerji verimliliği yüksek ve ekolojik malzeme kullanımını optimize eden tasarımlar geliştireceksiniz. Yeşil bina sertifikasyon süreçlerini (LEED, BREEAM) yöneteceksiniz.\n\n" +
            "3. Sorumluluklar\n- Ekolojik tasarım prensiplerini mimari projelere entegre etmek.\n- Enerji modellemesi ve gün ışığı analizleri yapmak.\n- Sürdürülebilir malzeme kütüphanesi oluşturmak ve tedarikçileri denetlemek.\n\n" +
            "4. Gereksinimler\n- Mimarlık mezunu, sürdürülebilirlik odaklı yüksek lisans veya sertifika sahibi.\n- Yeşil bina projelerinde en az 5 yıl deneyim.\n- BIM (Revit) ve enerji simülasyon araçlarına hakimiyet.\n\n" +
            "5. Neden Bu Şirket?\nDünyayı değiştiren projelerde yer alma şansı, Ar-Ge odaklı çalışma kültürü ve profesyonel gelişim destekleri.\n\n" +
            "6. İşe Alım Süreci\nBaşvuru → Portfolyo İncelemesi → Teknik Mülakat → Teklif");

        createJobAd(ekoyapi, civilEngineerTitle, cities.get(1), types.get(0), 
            "1. Giriş\nBüyük ölçekli, yenilikçi inşaat projelerimizde saha ve ofis süreçlerini koordine edecek deneyimli bir inşaat mühendisi arıyoruz.\n\n" +
            "2. Rol Tanımı\nSenior Civil Engineer olarak, yapısal tasarımların doğruluğunu denetleyecek, şantiye süreçlerini proje takvimine uygun şekilde yönetecek ve maliyet kontrolü yapacaksınız. Güvenlik ve kalite standartlarından asla ödün vermeyeceksiniz.\n\n" +
            "3. Sorumluluklar\n- Statik projelerin kontrolü ve onay süreçlerini yönetmek.\n- Taşeron ekiplerin koordinasyonunu ve hakedişlerini takip etmek.\n- İş programı (Primavera/MS Project) yönetimi.\n\n" +
            "4. Gereksinimler\n- İnşaat Mühendisliği mezunu, en az 10 yıl saha ve ofis deneyimi.\n- Büyük ölçekli üst yapı projelerinde tecrübe.\n- Çözüm odaklı ve güçlü liderlik vasıfları.\n\n" +
            "5. Neden Bu Şirket?\nKurumsal disiplin, güçlü finansal yapı ve mühendislik kariyerinizde 'zirve' projeler.\n\n" +
            "6. İşe Alım Süreci\nBaşvuru → Teknik Mülakat → Saha Ziyareti → Teklif");

        // --- COMPANY 13: Mavi Bulut Danışmanlık ---
        Employer mavibulut = createEmployer("talent@mavibulut.com", "Mavi Bulut Danışmanlık", "www.mavibulut.com", "2125550013", employerRole);
        JobTitle remoteStrategyTitle = getOrCreateJobTitle("Remote Work Strategy Consultant", hrCategory);
        JobTitle digitalCultureTitle = getOrCreateJobTitle("Digital Culture & Engagement Lead", hrCategory);

        createJobAd(mavibulut, remoteStrategyTitle, cities.get(0), types.get(2), 
            "1. Giriş\nMavi Bulut, şirketlerin hibrit ve uzaktan çalışma modellerine geçişini yöneten, yeni nesil iş dünyasının mimarı bir danışmanlık firmasıdır.\n\n" +
            "2. Rol Tanımı\nRemote Work Strategy Consultant olarak, kurumsal müşterilerimizin çalışma modellerini analiz edecek, uzaktan çalışma politikaları kurgulayacak ve bu geçişin verimlilik üzerindeki etkilerini ölçeceksiniz. Dijital iş akışlarını optimize edeceksiniz.\n\n" +
            "3. Sorumluluklar\n- Şirketlere özel hibrit çalışma anayasaları oluşturmak.\n- Uzaktan çalışma araçlarının (Slack, Notion, Miro vb.) entegrasyonuna rehberlik etmek.\n- Uzaktan yönetim ve iletişim eğitimleri vermek.\n\n" +
            "4. Gereksinimler\n- İK, İşletme veya Psikoloji mezunu, organizasyonel gelişim tecrübesi olan.\n- Uzaktan çalışma dinamiklerine dair derin bilgi ve merak.\n- Güçlü analiz ve sunum becerisi.\n\n" +
            "5. Neden Bu Şirket?\nKendisi de tamamen uzaktan çalışan bir ekip, esnek dünya görüşü ve geleceğin iş modelini bugünden inşa etme fırsatı.\n\n" +
            "6. İşe Alım Süreci\nBaşvuru → Görüntülü Tanışma → Vaka Çözümü (Remote Tooling) → Teklif");

        createJobAd(mavibulut, digitalCultureTitle, cities.get(0), types.get(2), 
            "1. Giriş\nFiziksel ofislerin olmadığı bir dünyada şirket kültürünü nasıl canlı tutarız? Bu sorunun cevabını bizimle birlikte verecek bir uzman arıyoruz.\n\n" +
            "2. Rol Tanımı\nDigital Culture & Engagement Lead olarak, uzaktan çalışan ekiplerin bağlılığını artıracak dijital etkinlikler tasarlayacak, iç iletişim kanallarını yönetecek ve çalışan deneyimini ekranların ötesine taşıyacaksınız.\n\n" +
            "3. Sorumluluklar\n- Sanal ekip kurma (team building) aktiviteleri organize etmek.\n- Şirket içi dijital bülten ve topluluk yönetimini üstlenmek.\n- Çalışan memnuniyet anketlerini analiz ederek aksiyon planları çıkarmak.\n\n" +
            "4. Gereksinimler\n- Etkinlik yönetimi veya iç iletişim alanında en az 4 yıl deneyim.\n- Dijital araçları yaratıcı şekilde kullanabilen.\n- Yüksek enerjili ve insan odaklı.\n\n" +
            "5. Neden Bu Şirket?\nDünyanın her yerinden çalışma özgürlüğü, inovatif bir İK vizyonu ve sürekli eğlence.\n\n" +
            "6. İşe Alım Süreci\nBaşvuru → Yaratıcı Ödev → Ekip Mülakatı → Teklif");

        // --- COMPANY 14: Hız Sabitleyici Lojistik ---
        Employer hizsabitleyici = createEmployer("careers@hizsabitleyici.com", "Hız Sabitleyici Lojistik", "www.hizsabitleyici.com", "2125550014", employerRole);
        JobTitle optimizerTitle = getOrCreateJobTitle("Logistics Network Optimizer", opCategory);
        JobTitle fleetManagerTitle = getOrCreateJobTitle("Autonomous Fleet Manager", opCategory);

        createJobAd(hizsabitleyici, optimizerTitle, cities.get(1), types.get(0), 
            "1. Giriş\nHız Sabitleyici, lojistiği matematik ve yapay zeka ile yeniden tanımlayan, Türkiye'nin en akıllı dağıtım ağına sahip teknoloji şirketidir.\n\n" +
            "2. Rol Tanımı\nLogistics Network Optimizer olarak, büyük veriyi kullanarak rotaları optimize edecek, depo lokasyonlarını belirleyecek ve teslimat sürelerini saniyeler bazında iyileştirecek algoritmik modeller üzerinde çalışacaksınız.\n\n" +
            "3. Sorumluluklar\n- Operasyonel verimlilik analizleri yaparak maliyet düşürücü projeler geliştirmek.\n- Dağıtım ağının kapasite planlamasını yapmak.\n- Tedarik zinciri simülasyonları yürütmek.\n\n" +
            "4. Gereksinimler\n- Endüstri Mühendisliği veya Yöneylem Araştırması mezunu.\n- SQL, Python ve optimizasyon yazılımlarına (Gurobi, CPLEX vb.) hakimiyet.\n- Veri görselleştirme becerisi.\n\n" +
            "5. Neden Bu Şirket?\nVeriyle yönetilen dev bir operasyon, lojistik teknolojilerinde liderlik ve mühendislik odaklı bir yaklaşım.\n\n" +
            "6. İşe Alım Süreci\nBaşvuru → Sayısal Test → Teknik Mülakat → Direktör Görüşmesi → Teklif");

        createJobAd(hizsabitleyici, fleetManagerTitle, cities.get(0), types.get(0), 
            "1. Giriş\nOtonom araçların ve dronların yönetildiği bir geleceğe hazır mısınız? Filomuzun teknolojik dönüşümüne liderlik edecek bir yönetici arıyoruz.\n\n" +
            "2. Rol Tanımı\nAutonomous Fleet Manager olarak, filomuza dahil olan akıllı araçların operasyonel hazırlığını, bakım süreçlerini ve uzaktan izleme sistemlerini yöneteceksiniz. Otonom sürüş verilerini analiz ederek operasyonel güvenliği sağlayacaksınız.\n\n" +
            "3. Sorumluluklar\n- Otonom araç filosunun performans takibi ve raporlaması.\n- Teknik bakım ekiplerinin ve yazılım güncellemelerinin koordinasyonu.\n- Yeni nesil mobilite teknolojilerinin filoya entegrasyonu.\n\n" +
            "4. Gereksinimler\n- Mühendislik mezunu, mobilite veya robotik sistemlere ilgi duyan.\n- Filo yönetimi veya operasyonel mükemmellik alanında deneyim.\n- Teknoloji okuryazarlığı yüksek.\n\n" +
            "5. Neden Bu Şirket?\nTürkiye'nin ilk otonom lojistik ekibinde yer alma fırsatı, teknoloji odaklı yan haklar ve gelecek vizyonu.\n\n" +
            "6. İşe Alım Süreci\nBaşvuru → Teknik Vizyon Mülakatı → Saha Tanıtımı → Teklif");

        // --- COMPANY 15: Gastronomi Dünyası ---
        Employer gastronomi = createEmployer("kariyer@gastronomidunyasi.com", "Gastronomi Dünyası", "www.gastronomidunyasi.com", "2125550015", employerRole);
        JobTitle opsLeadTitle = getOrCreateJobTitle("Food & Beverage Operations Lead", opCategory);
        JobTitle culinaryDirectorTitle = getOrCreateJobTitle("Culinary Director (Executive Chef)", opCategory);

        createJobAd(gastronomi, opsLeadTitle, cities.get(0), types.get(0), 
            "1. Giriş\nGastronomi Dünyası, gurme lezzetleri kurumsal disiplinle birleştiren, Türkiye'nin en seçkin restoran zincirlerinden biridir. Operasyonel mükemmelliği lezzetle buluşturuyoruz.\n\n" +
            "2. Rol Tanımı\nF&B Operations Lead olarak, restoranlarımızın operasyonel standartlarını belirleyecek, servis kalitesini denetleyecek ve karlılık hedeflerini yöneteceksiniz. Misafir memnuniyetini bir tutku haline getirecek süreçler kurgulayacaksınız.\n\n" +
            "3. Sorumluluklar\n- Çoklu şube yönetimi ve denetimi.\n- Tedarik zinciri ve maliyet (Food Cost) analizi.\n- Servis standartlarının belirlenmesi ve eğitimlerin verilmesi.\n- Yeni şube açılış süreçlerini yönetmek.\n\n" +
            "4. Gereksinimler\n- Turizm ve Otel İşletmeciliği mezunu, en az 8 yıl üst düzey restoran yönetimi deneyimi.\n- Finansal raporlama ve bütçe yönetimi becerisi.\n- Gastronomi dünyasına tutkuyla bağlı.\n\n" +
            "5. Neden Bu Şirket?\nPrestijli bir marka, kurumsal gelişim olanakları ve Türkiye'nin en iyi şefleriyle çalışma fırsatı.\n\n" +
            "6. İşe Alım Süreci\nBaşvuru → Panel Mülakat → Saha Denetimi (Gizli Müşteri Gözüyle) → Teklif");

        createJobAd(gastronomi, culinaryDirectorTitle, cities.get(0), types.get(0), 
            "1. Giriş\nMutfaklarımızın orkestra şefi olacak, menülerimize ruh katacak vizyoner bir Executive Chef arıyoruz.\n\n" +
            "2. Rol Tanımı\nCulinary Director olarak, tüm şubelerimizin menü tasarımından, reçete standartlarından ve mutfak operasyonunun kalitesinden sorumlu olacaksınız. Mevsimsel trendleri takip ederek yaratıcı ve karlı menüler oluşturacaksınız.\n\n" +
            "3. Sorumluluklar\n- Yeni reçete geliştirme ve maliyetlendirme.\n- Mutfak ekiplerinin eğitimi ve yönetimi.\n- Gıda güvenliği ve hijyen standartlarının (HACCP) en üst seviyede tutulması.\n\n" +
            "4. Gereksinimler\n- Gastronomi ve Mutfak Sanatları mezunu, uluslararası mutfaklarda deneyim sahibi.\n- Liderlik vasfı yüksek ve öğretmeye hevesli.\n- Yaratıcı, yenilikçi ve detaycı.\n\n" +
            "5. Neden Bu Şirket?\nEn kaliteli malzemelerle çalışma özgürlüğü, kendi imza yemeklerinizi yaratma şansı ve güçlü bir mutfak altyapısı.\n\n" +
            "6. İşe Alım Süreci\nBaşvuru → Portfolyo (Tabak Sunumları) → Tadım Paneli → Final Görüşme → Teklif");

        // --- COMPANY 16: Focus Sağlık ---
        Employer focus = createEmployer("hr@focussaglik.com", "Focus Sağlık Teknolojileri", "www.focussaglik.com", "2125550016", employerRole);
        JobTitle healthDataAnalystTitle = getOrCreateJobTitle("Health Data Analyst", itCategory);
        JobTitle mobileDevTitle = getOrCreateJobTitle("Senior Mobile Developer (Flutter)", itCategory);

        createJobAd(focus, healthDataAnalystTitle, cities.get(0), types.get(2), 
            "1. Giriş\nFocus Sağlık, yapay zeka ile erken teşhis ve giyilebilir teknolojiler üzerinde çalışan bir Ar-Ge merkezidir. Verilerle hayat kurtarmaya var mısınız?\n\n" +
            "2. Rol Tanımı\nHealth Data Analyst olarak, giyilebilir cihazlardan gelen biyometrik verileri analiz edecek, anomali tespiti yapan modeller geliştirecek ve klinik araştırmalara veri desteği sağlayacaksınız. Sağlık verisinin gizliliği ve güvenliği önceliğiniz olacak.\n\n" +
            "3. Sorumluluklar\n- Büyük ölçekli sağlık verisi setlerini temizlemek ve analiz etmek.\n- Tahminleme modelleri ve sağlık dashboardları oluşturmak.\n- Medikal ekiplerle birlikte çalışarak veri çıktılarını klinik anlamlandırmaya dönüştürmek.\n\n" +
            "4. Gereksinimler\n- Biyoenformatik, İstatistik veya Bilgisayar Mühendisliği mezunu.\n- Python (Pandas, Scikit-learn) ve SQL uzmanlığı.\n- Sağlık verisi standartları (HL7, FHIR) hakkında bilgi sahibi.\n\n" +
            "5. Neden Bu Şirket?\nToplumsal fayda sağlayan projeler, bilimsel bir çalışma ortamı ve sağlık teknolojilerinde öncü olma fırsatı.\n\n" +
            "6. İşe Alım Süreci\nBaşvuru → Veri Analizi Testi → Teknik Mülakat → Medikal Panel → Teklif");

        createJobAd(focus, mobileDevTitle, cities.get(0), types.get(2), 
            "1. Giriş\nKullanıcılarımızın sağlığını her an takip edebileceği, yüksek performanslı ve kullanıcı dostu mobil uygulamalar geliştirecek bir Senior Mobile Developer arıyoruz.\n\n" +
            "2. Rol Tanımı\nFlutter ekosisteminde uzmanlaşmış olarak, Focus mobil uygulamalarının mimarisini kuracak, Bluetooth cihaz entegrasyonlarını yönetecek ve veri güvenliği yüksek mobil çözümler üreteceksiniz.\n\n" +
            "3. Sorumluluklar\n- Cross-platform mobil uygulamalar geliştirmek (Flutter).\n- IoT/Giyilebilir cihazlarla düşük enerjili Bluetooth (BLE) haberleşme sistemleri kurmak.\n- Mobil uygulama performans optimizasyonu ve güvenliğini sağlamak.\n\n" +
            "4. Gereksinimler\n- Flutter ve Dart ile en az 4 yıl profesyonel uygulama geliştirme deneyimi.\n- State management (Bloc/Provider) ve mobil veritabanı tecrübesi.\n- Uygulama mağazası (AppStore/PlayStore) süreçlerine hakimiyet.\n\n" +
            "5. Neden Bu Şirket?\nEn yeni mobil teknolojilerle çalışma imkanı, uzaktan çalışma esnekliği ve sağlık devrimine teknolojik katkı.\n\n" +
            "6. İşe Alım Süreci\nBaşvuru → Teknik Case Study → Live Coding → Ekip Tanışması → Teklif");
    }

    private Employer createEmployer(String email, String companyName, String webAddress, String phone, Role role) {
        return employerDao.findByEmail(email).orElseGet(() -> {
            Employer employer = new Employer();
            employer.setEmail(email);
            employer.setPassword(passwordEncoder.encode("123456"));
            employer.setRole(role);
            employer.setCompanyName(companyName);
            employer.setWebAddress(webAddress);
            employer.setPhoneNumber(phone);
            return employerDao.save(employer);
        });
    }

    private JobTitle getOrCreateJobTitle(String title, Category category) {
        return jobTitleDao.findByTitle(title).orElseGet(() -> jobTitleDao.save(new JobTitle(null, title, null, category)));
    }

    private void createJobAd(Employer employer, JobTitle title, City city, TypeOfWork type, String description) {
        // Check if this ad already exists to avoid duplicates
        if (jobAdvertisementDao.existsByEmployerAndJobTitle(employer, title)) return;

        JobAdvertisement ad = new JobAdvertisement();
        ad.setEmployer(employer);
        ad.setJobTitle(title);
        ad.setCity(city);
        ad.setTypeOfWork(type);
        ad.setDescription(description);
        ad.setStartDate(LocalDate.now());
        ad.setEndDate(LocalDate.now().plusMonths(2));
        ad.setOpenPositions(1);
        ad.setStatus(true);
        jobAdvertisementDao.save(ad);
    }

    private void seedSamples() {
        // Keeping it for potential backward compatibility or if needed, 
        // but now redirected to seedRealisticData
    }
}
