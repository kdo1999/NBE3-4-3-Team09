# 프로그래머스 데브코스 9팀 3차 프로젝트

## Team

<table>
  <tr>
    <td align="center"><a href="https://github.com/zelly-log"><img src="https://avatars.githubusercontent.com/u/188554914?v=4" width="100px;" alt="이재이"/><br /><sub><b>이재이</b></sub></a></td>
    <td align="center"><a href="https://github.com/kdo1999"><img src="https://avatars.githubusercontent.com/u/122996064?v=4" width="100px;" alt="김동오"/><br /><sub><b>김동오</b></sub></a></td>
    <td align="center"><a href="https://github.com/sungyeong98"><img src="https://avatars.githubusercontent.com/u/149341161?v=4" width="100px;" alt="안선경"/><br /><sub><b>안선경</b></sub></a></td>
    <td align="center"><a href="https://github.com/Gonhub9"><img src="https://avatars.githubusercontent.com/u/172102165?v=4" width="100px;" alt="김현곤"/><br /><sub><b>김현곤</b></sub></a></td>
    <td align="center"><a href="https://github.com/richard3251"><img src="https://avatars.githubusercontent.com/u/77492691?v=4" width="100px;" alt="정오연"/><br /><sub><b>정오연</b></sub></a></td>
    <td align="center"><a href="https://github.com/Janghyeonsuk"><img src="https://avatars.githubusercontent.com/u/74901548?v=4" width="100px;" alt="장현석"/><br /><sub><b>장현석</b></sub></a></td>

  </tr>
</table>

--- 

## 프로젝트 주제

<b>사람인 API를 활용하여 IT 직군 채용 공고를 수집하고 제공하며, <br>사용자들이 각 공고별로 모임을 생성하고 정보를 나눌 수 있는 서비스입니다.

---

## 프로젝트 목적

- 기존 Java 프로젝트 Kotlin으로 마이그레이션
- 2차 프로젝트에서 완성하지 못했던 기능 구현
- 채팅 기능 MongoDB, Redis를 이용한 성능 개선
- 채용 공고 사람인 API 이용하는 스케줄러 Redis 캐싱을 이용한 성능 개선
- Oracle Cloud 환경에서 Github Actions, Jenkins, Docker를 이용한 배포 자동화

---

## 기술 스택

### Backend

<img src="https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=Spring&logoColor=white">
<img src="https://img.shields.io/badge/security-6DB33F?style=for-the-badge&logo=spring-security&logoColor=white">
<img src="https://img.shields.io/badge/MongoDB-47A248?style=for-the-badge&logo=MongoDB&logoColor=white">
<img src="https://img.shields.io/badge/Mysql-4479A1?style=for-the-badge&logo=Mysql&logoColor=white">
<img src="https://img.shields.io/badge/REDIS-FF4438?style=for-the-badge&logo=redis&logoColor=white">
<br>
<img src="https://img.shields.io/badge/kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white">
<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=openjdk&logoColor=white">

---

### Etc

<img src="https://img.shields.io/badge/notion-000000?style=for-the-badge&logo=notion&logoColor=white">
<img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white">
<img src="https://img.shields.io/badge/docker-2496ED?style=for-the-badge&logo=docker&logoColor=white">
<img src="https://img.shields.io/badge/actions-2088FF?style=for-the-badge&logo=github-actions&logoColor=white">
<img src="https://img.shields.io/badge/jenkins-D24939?style=for-the-badge&logo=jenkins&logoColor=white">
<img src="https://img.shields.io/badge/discord-5865F2?style=for-the-badge&logo=discord&logoColor=white">

---

### 아키텍처

![프로그래머스 팀9 3차 프로젝트 아키텍처 drawio](https://github.com/user-attachments/assets/7ef2d567-3776-42ae-9ce1-4b8910b8bd2b)

---

### 기능 요구사항

![dev](https://github.com/user-attachments/assets/6d7c0aa1-ce50-4135-af2f-23287c87afa6)

---

### Erd

![dev](https://github.com/user-attachments/assets/cca31929-7d36-42ec-86b7-271f81697dcf)

---
### 역할 분담

김동오
- 공통 클래스 구현
- JobPostingScheduler 성능 개선
- JobPosting 마이그레이션
- MailService 코루틴으로 리팩토링
- RecruitmentSchedulerService 기능 구현
- CI/CD 구축

김현곤
- Category 마이그레이션
- Recruitment 마이그레이션

안선경
- Security 마이그레이션
- User 마이그레이션
- 부하 테스트 측정

이재이
- Post 마이그레이션

장현석
- Chat 성능 개선
- Chat 마이그레이션
- Recruitment 마이그레이션
- RecruitmentSchedulerService 기능 구현

정오연
- Comment 마이그레이션

---

### 트러블 슈팅

<details>
<summary>Docker Mysql 데이터 백업 자동화 - 김동오</summary>
<div markdown="1">

# 🛠 트러블슈팅 기록

## 1. 문제 요약

**발생 일시:** 2025/03/06

**증상: Oracle Cloud 인스턴스에 실행중인 Mysql 강제 종료 현상**

- 강제 종료도 문제지만 데이터 백업이 제대로 이루어지지 않아서 강제 종료 이후엔 DB가 다 날라가는 문제 발생

(문제가 발생한 현상을 간단히 설명)

## 2. 원인 분석

- 메모리 부족 및 최대 연결 수 설정 문제일 가능성 발견

![log](https://github.com/user-attachments/assets/b097a0fe-d2c7-48f3-bdbf-6bbf3c2d2fb0)

## 3. 해결 방법

- Mysql 컨테이너에 bash로 접속해서 my.cnf 파일에 아래 설정 추가
    - [참고 URL](https://manage.accuwebhosting.com/knowledgebase/2320/How-to-Fix-Error-Forcing-close-of-thread-310-user-andsharp039rootandsharp039-in-MySQL.html)

    ```bash
    innodb_buffer_pool_size=512M  # InnoDB 버퍼 풀 크기 설정
    max_connections=100  #
    ```

    ```bash
    # 컨테이너 터미널 접속
    docker exec -it 컨테이너명 bash
    
    # 설정 파일 출력
    cat my.cnf
    
    # 출력 결과 복사 후 위에 설정 추가해서 덮어쓰기
    echo -e "
    # For advice on how to change settings please see
    # http://dev.mysql.com/doc/refman/9.2/en/server-configuration-defaults.html
    
    [mysqld]
    innodb_buffer_pool_size=512M  # InnoDB 버퍼 풀 크기 설정
    max_connections=100  #
    # Remove leading # and set to the amount of RAM for the most important data
    # cache in MySQL. Start at 70% of total RAM for dedicated server, else 10%.
    # innodb_buffer_pool_size = 128M
    #
    # Remove leading # to turn on a very important data integrity option: logging
    # changes to the binary log between backups.
    # log_bin
    #
    # Remove leading # to set options mainly useful for reporting servers.
    # The server defaults are faster for transactions and fast SELECTs.
    # Adjust sizes as needed, experiment to find the optimal values.
    # join_buffer_size = 128M
    # sort_buffer_size = 2M
    # read_rnd_buffer_size = 2M
    
    host-cache-size=0
    skip-name-resolve
    datadir=/var/lib/mysql
    socket=/var/run/mysqld/mysqld.sock
    secure-file-priv=/var/lib/mysql-files
    user=mysql
    
    pid-file=/var/run/mysqld/mysqld.pid
    [client]
    socket=/var/run/mysqld/mysqld.sock" > my.cnf
    
    # 추가한 설정 들어갔는지 확인
    cat my.cnf
    
    exit
    
    # 컨테이너 재시작
    docker stop 컨테이너명
    docker start 컨테이너명
    ```

- DB 자동 백업 설정으로 추후 다시 발생하더라도 복구 가능하게 조치
    - 쉘 스크립트 작성

    ```bash
    vi db_backup/backup.sh
    # 1. I 눌러서 Insert mode에서 아래 내용 복사
    # 2. :wq + Enter로 저장
    ```

    ```bash
    DEV_FILE_NAME=dev_backup_`date +"%Y%m%d%H%M%S"`
    TESTDB_FILE_NAME=testdb_backup_`date +"%Y%m%d%H%M%S"`
    
    # mysql db 데이터 백업
    docker exec mysql-server mysqldump -u root -ptest1 dev > $DEV_FILE_NAME.sql;
    docker exec mysql-server mysqldump -u root -ptest1 testdb > $TESTDB_FILE_NAME.sql;
    
    # 백업 디렉토리에서 백업 파일들 중 가장 최신 3개를 제외하고 나머지 삭제
    ls -t testdb_backup_*.sql | tail -n +4 | xargs rm -ff
    ls -t dev_backup_*.sql | tail -n +4 | xargs rm -f
    ```

    - 자동 실행 설정

    ```bash
    vi /etc/crontab
    # 1. I 눌러서 Insert mode에서 아래 내용 복사
    # 2. :wq + Enter로 저장
    
    ```

    ```bash
    # 1시간마다 자동 실행
    # */60 * * * * 는 cron 표현법이고 따로 찾아보시길 바랍니다.
    # root -> 사용자
    # /var/lib/docker/volumes/mysql-data-vol/db_backup/backup.sh -> 쉘 스크립트 경로
    */60 * * * *    root    /var/lib/docker/volumes/mysql-data-vol/db_backup/backup.sh
    
    ```

## 4. 결과 및 추가 조치

- 현재까지는 서버 다운 없이 정상 가동중

## 5. 회고 및 예방 조치

- 실제 운영 서버라면 당연히 무슨 일이 일어날지 모르기 때문에 DB는 자동으로 백업 조치를 해주어야하는데 볼륨 설정만 해두고 백업을 하지 않았어서 발생했던 거 같다.

추후에는 컨테이너 띄우면서 초기 백업 작업까지 해주어야겠다.

- 최대 커넥션 수랑 InnoDB 버퍼 풀 사이즈는 정확히 어떻게 동작하는지 또 설정 값을 어느정도로 두어야 하는지는 아직 확인해보지 못해서 추후 학습해볼 예정이다.

</div>
</details>

<details>
<summary>사람인 API 데이터 저장하는 로직 성능 개선 - 김동오</summary>
<div markdown="1">

# 🛠 트러블슈팅 기록

## 1. 문제 요약

**발생 일시:** 2025-02-19

**증상:**

(문제가 발생한 현상을 간단히 설명)

## 2. 원인 분석

- 기존 플로우
    - **사람인 API 호출**→`JobPosting`엔티티 변환
    - **JobPosting 전체 저장**(**병목 발생 가능**)
    - **응답받은 Job 데이터를 Map에 저장**(`key: Job.id`,`value: Job 데이터`)
    - **저장된 JobPosting을 순회하면서 추가 처리**
        - `JobPosting.jobId`와 일치하는 Job을`Map`에서 가져옴
        - Job에서`jobCode`를 가져와`","`로 분리
        - 분리된`jobCode`를 순회
            - **`Job_Skill`테이블에서 jobCode 조회**(**병목 발생 가능**)
            - 조회된 데이터를`JobPostingJobSkillList`에 추가 후 저장 (**병목 발생 가능**)
    - **전체 데이터 처리 후 남은 데이터가 있으면 재귀 호출**
- 주요 원인
    - **쿼리 호출 횟수가 많음**
        - `JobPosting`저장 시 1회`INSERT`
        - `JobCode`조회 시 최소 1회, 최대`jobCodeArray.length`만큼 추가`SELECT`
        - `더티 체킹`으로 인한 추가`UPDATE`발생
    - **예상 쿼리 호출량 (1,100개 기준)**
    - 최소**3,300번**, jobCode가 5개씩 있는 경우**7,700번**발생 가능

## 3. 해결 방법

- 전체 저장시 JobSkill까지 초기화 후 저장 (더티체킹 발생하지 않게 수정)
- `Redis`에`JobSkill`을 저장해서 캐싱 처리
- 기존 코드
    - 기존 코드

        ```java
        @Slf4j
        @Service
        @RequiredArgsConstructor
        public class SchedulerService {
        
            private final JobSkillRepository jobSkillRepository;
            private final JobPostingRepository jobPostingRepository;
            private final ObjectMapper objectMapper;
            private final RestTemplate restTemplate;
            private final RetryTemplate retryTemplate;
        
            // URI로 조합할 OPEN API URL
            private final String API_URL = "<https://oapi.saramin.co.kr/job-search>";
        
            // URI로 조합할 apiKey
            @Value("${api.key}")
            private String apiKey;
        
            // URI로 조합할 한 페이지당 가져올데이터 수
            @Value("${api.count}")
            private Integer count;
        
            /**
             * 매일 자정(00:00)에 실행될 스케줄러 메서드입니다.
             * <p>
             * - retryTemplate.execute(context -> { ... }) -> API 요청이 실패할 경우 재시도를 수행하는 `RetryTemplate`을
             * 사용합니다. - processJobPostings (totalCount, totalJobs, pageNumber) -> API에서 채용 공고 데이터를 가져와
             * 데이터베이스에 저장하는 핵심 로직을 실행합니다.
             */
            @Scheduled(cron = "0 0 0 * * ?", zone = "Asia/Seoul")
            @Transactional
            public void savePublicData() {
                retryTemplate.execute(context -> {
                    int pageNumber = 0;
                    int totalCount = 0;
                   	int totalJobs = 1100; //1. 1,100개 기준 성능 측정
        
        			LocalDateTime start = LocalDateTime.now();
        			processJobPostings(totalCount, totalJobs, pageNumber);
        			LocalDateTime end = LocalDateTime.now();
        
                    // 시간 차이 계산
        			Duration duration = Duration.between(start, end);
        
                    // 결과값 출력
        			log.info("작업 실행 시간: {} 밀리초", duration.toMillis());
        			log.info("작업 실행 시간: {} 초", duration.getSeconds());
        
                    return null;
                });
            }
        
            /**
             * - 클래스 내에서 핵심로직이며, fetchJobPostings() 메소드를 통해 가져온 채용공고 데이터들을 저장하기위한 List<JobPosting>,
             * List<JobSkill> 로 변환하여, 저장하도록 하는 메서드이다.
             * - 오늘 가져올수있는 총 공고수(totalJobs) 보다 데이텁베이스에 저장된 공고수(totalCount) 크면 callBack 함수가 멈춘다.
             *
             * @param totalCount 현재 저장된 공고수
             * @param totalJobs  오늘 총 공고 수
             * @param pageNumber 현재 페이지 번호
             */
            public void processJobPostings(int totalCount, int totalJobs, int pageNumber) {
                Jobs jobs = fetchJobPostings(pageNumber, count);
        
                // JobPosting 클래스로 담기
                List<JobPosting> jobPostingList = jobs.getJobsDetail().getJobList().stream()
                    .map(Job::toEntity)
                    .toList();
        
                // 전체 저장
                List<JobPosting> savedJobPostingList = saveNewJobs(jobPostingList);
        
                //JSON 응답 파싱
                List<Job> jobList = jobs.getJobsDetail().getJobList();
                Map<Long, Job> jobMap = jobList.stream()
                    .collect(Collectors.toMap(job -> Long.parseLong(job.getId()), job -> job));
        
                for (JobPosting jobPosting : savedJobPostingList) {
        
                    //채용 공고랑 jobPosting이랑 일치하는 애 찾는 if문
                    // 한 페이지에 해당하는 110개의 데이터를 방금 저장한 공고들인 jobPosting과 비교하여, 손수 job-code의 code를 꺼내기 위한 작업.
                    Job findJob = jobMap.get(jobPosting.getJobId());
                    String jobCode = findJob.getPositionDto().getJobCode().getCode();
        
                    //여러개면 , 기준으로 짜르기
                    String[] jobCodeArray = jobCode.split(",");
        
                    for (String s : jobCodeArray) {
                        // db에 저장된 jobSkill, code로 조회
                        Optional<JobSkill> jobSkillOptional = jobSkillRepository.findByCode(
                            Integer.parseInt(s.trim()));
        
                        //jobSkill DB에 없다면
                        if (jobSkillOptional.isEmpty()) {
                            continue;
                        } else {
                            JobSkill jobSkill = jobSkillOptional.get();
        
                            //JobPosting에 jobskill 설정
                            //더티 체킹으로 인해 업데이트 쿼리 자동 발생
                            jobPosting.getJobPostingJobSkillList().add(
                                JobPostingJobSkill.builder()
                                    .jobPosting(jobPosting)
                                    .jobSkill(jobSkill)
                                    .build());
                        }
                    }
                }
        
                //총 가져와야되는 개수 초기화
                if (totalJobs == Integer.MAX_VALUE) {
                    totalJobs = Integer.parseInt(jobs.getJobsDetail().getTotal());
                }
        
                totalCount += jobPostingList.size();
        
                if (totalCount < totalJobs) {
                    processJobPostings(totalCount, totalJobs, ++pageNumber);
                }
            }
        
            /**
             * 지정된 페이지 번호와 가져올 데이터 개수를 기준으로 채용공고 데이터를 가져오는 메서드입니다.
             * <p>
             * - restTemplate : 주어진 URI로 채용공고 api 서버에 GET 요청을 보내, 응답 데이터를 받아오는 역할수행 - objectMapper : JSON
             * 문자열을 Jobs 객체로 변환하는 즉 역직렬화 역할수행.
             *
             * @param pageNumber 현재 페이지 번호
             * @param count      가져올 데이터 개수
             */
            private Jobs fetchJobPostings(int pageNumber, int count) {
        
                URI uri = UriComponentsBuilder.fromHttpUrl(API_URL)
                    .queryParam("access-key", apiKey)
                    //.queryParam("published", getPublishedDate())
                    .queryParam("job_mid_cd", "2")
                    .queryParam("start", pageNumber) // 현재 페이지숫자
                    .queryParam("count", count)
                    .queryParam("fields", "count")//한 번 호출시 가지고 오는 데이터 양
                    .build()
                    .encode()
                    .toUri();
        
                try {
                    String jsonResponse = restTemplate.getForObject(uri, String.class);
        
                    Jobs dataResponse = objectMapper.readValue(jsonResponse, Jobs.class);
        
                    if (dataResponse.getJobsDetail() == null || dataResponse.getJobsDetail().getJobList()
                        .isEmpty()) {
                        log.error(GlobalErrorCode.NO_DATA_RECEIVED.getMessage());
                        throw new GlobalException(GlobalErrorCode.NO_DATA_RECEIVED);
                    }
        
                    return dataResponse;
        
                } catch (JsonProcessingException e) {
                    log.error("JSON 파싱 실패", e);
                    throw new GlobalException(GlobalErrorCode.JSON_PARSING_FAILED);
                }
        
            }
        
            /**
             * scheduler가 자정에 실행되기 때문에 전날 데이터를 가져오게 만든 메서드
             */
            private String getPublishedDate() {
                // 전날데이터
                LocalDate today = LocalDate.now().minusDays(1);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                return today.format(formatter);
            }
        
            /**
             * JobPosting, JobSkill 데이터들을 데이터베이스에 저장하기위한 메서드
             *
             * @param newJobs 가공된 JobPosting 데이터 리스트
             */
            private List<JobPosting> saveNewJobs(List<JobPosting> newJobs) {
                try {
                    List<JobPosting> savedJobPostingList = jobPostingRepository.saveAll(newJobs);
                    log.info("총 {}개의 공고를 저장했습니다.", savedJobPostingList.size());
                    return savedJobPostingList;
                } catch (Exception e) {
                    log.error(GlobalErrorCode.DATABASE_SAVE_FAILED.getMessage(), e);
                    throw new GlobalException(GlobalErrorCode.DATABASE_SAVE_FAILED);
                }
            }
        
        }
        
        ```

- 저장 메소드 위치 수정
    - 저장 메소드 위치 수정 후 코드

        ```java
        @Slf4j
        @Service
        @RequiredArgsConstructor
        public class SchedulerService {
        
            private final JobSkillRepository jobSkillRepository;
            private final JobPostingRepository jobPostingRepository;
            private final ObjectMapper objectMapper;
            private final RestTemplate restTemplate;
            private final RetryTemplate retryTemplate;
        
            // URI로 조합할 OPEN API URL
            private final String API_URL = "https://oapi.saramin.co.kr/job-search";
        
            // URI로 조합할 apiKey
            @Value("${api.key}")
            private String apiKey;
        
            // URI로 조합할 한 페이지당 가져올데이터 수
            @Value("${api.count}")
            private Integer count;
        
            /**
             * 매일 자정(00:00)에 실행될 스케줄러 메서드입니다.
             * <p>
             * - retryTemplate.execute(context -> { ... }) -> API 요청이 실패할 경우 재시도를 수행하는 `RetryTemplate`을
             * 사용합니다. - processJobPostings (totalCount, totalJobs, pageNumber) -> API에서 채용 공고 데이터를 가져와
             * 데이터베이스에 저장하는 핵심 로직을 실행합니다.
             */
            @Scheduled(cron = "0 0 0 * * ?", zone = "Asia/Seoul")
            @Transactional
            public void savePublicData() {
                retryTemplate.execute(context -> {
                    int pageNumber = 0;
                    int totalCount = 0;
        			int totalJobs = 1100; //1. 1100개 기준 성능 측정
        
        			LocalDateTime start = LocalDateTime.now();
        			processJobPostings(totalCount, totalJobs, pageNumber);
        			LocalDateTime end = LocalDateTime.now();
        
                    // 시간 차이 계산
        			Duration duration = Duration.between(start, end);
        
                    // 결과값 출력
        			log.info("작업 실행 시간: {} 밀리초", duration.toMillis());
        			log.info("작업 실행 시간: {} 초", duration.getSeconds());
                    
                    return null;
                });
            }
        
            /**
             * - 클래스 내에서 핵심로직이며, fetchJobPostings() 메소드를 통해 가져온 채용공고 데이터들을 저장하기위한 List<JobPosting>,
             * List<JobSkill> 로 변환하여, 저장하도록 하는 메서드이다.
             * - 오늘 가져올수있는 총 공고수(totalJobs) 보다 데이텁베이스에 저장된 공고수(totalCount) 크면 callBack 함수가 멈춘다.
             *
             * @param totalCount 현재 저장된 공고수
             * @param totalJobs  오늘 총 공고 수
             * @param pageNumber 현재 페이지 번호
             */
            public void processJobPostings(int totalCount, int totalJobs, int pageNumber) {
                Jobs jobs = fetchJobPostings(pageNumber, count);
        
                // JobPosting 클래스로 담기
                List<JobPosting> jobPostingList = jobs.getJobsDetail().getJobList().stream()
                    .map(Job::toEntity)
                    .toList();
        
                //JSON 응답 파싱
                List<Job> jobList = jobs.getJobsDetail().getJobList();
                Map<Long, Job> jobMap = jobList.stream()
                    .collect(Collectors.toMap(job -> Long.parseLong(job.getId()), job -> job));
        
                for (JobPosting jobPosting : jobPostingList) {
        
                    //채용 공고랑 jobPosting이랑 일치하는 애 찾는 if문
                    // 한 페이지에 해당하는 110개의 데이터를 방금 저장한 공고들인 jobPosting과 비교하여, 손수 job-code의 code를 꺼내기 위한 작업.
                    Job findJob = jobMap.get(jobPosting.getJobId());
                    String jobCode = findJob.getPositionDto().getJobCode().getCode();
        
                    //여러개면 , 기준으로 짜르기
                    String[] jobCodeArray = jobCode.split(",");
        
                    for (String s : jobCodeArray) {
                        // db에 저장된 jobSkill, code로 조회
                        Optional<JobSkill> jobSkillOptional = jobSkillRepository.findByCode(
                            Integer.parseInt(s.trim()));
        
                        //jobSkill DB에 없다면
                        if (jobSkillOptional.isEmpty()) {
                            continue;
                        } else {
                            JobSkill jobSkill = jobSkillOptional.get();
        
                            //JobPosting에 jobskill 설정
                            jobPosting.getJobPostingJobSkillList().add(
                                JobPostingJobSkill.builder()
                                    .jobPosting(jobPosting)
                                    .jobSkill(jobSkill)
                                    .build());
                        }
                    }
                }
        
                // 전체 저장 (위치 변경)
                List<JobPosting> savedJobPostingList = saveNewJobs(jobPostingList);
        
                //총 가져와야되는 개수 초기화
                if (totalJobs == Integer.MAX_VALUE) {
                    totalJobs = Integer.parseInt(jobs.getJobsDetail().getTotal());
                }
        
                totalCount += jobPostingList.size();
        
                if (totalCount < totalJobs) {
                    processJobPostings(totalCount, totalJobs, ++pageNumber);
                }
            }
        
            /**
             * 지정된 페이지 번호와 가져올 데이터 개수를 기준으로 채용공고 데이터를 가져오는 메서드입니다.
             * <p>
             * - restTemplate : 주어진 URI로 채용공고 api 서버에 GET 요청을 보내, 응답 데이터를 받아오는 역할수행 - objectMapper : JSON
             * 문자열을 Jobs 객체로 변환하는 즉 역직렬화 역할수행.
             *
             * @param pageNumber 현재 페이지 번호
             * @param count      가져올 데이터 개수
             */
            private Jobs fetchJobPostings(int pageNumber, int count) {
        
                URI uri = UriComponentsBuilder.fromHttpUrl(API_URL)
                    .queryParam("access-key", apiKey)
                    // .queryParam("published", getPublishedDate())
                    .queryParam("job_mid_cd", "2")
                    .queryParam("start", pageNumber) // 현재 페이지숫자
                    .queryParam("count", count)
                    .queryParam("fields", "count")//한 번 호출시 가지고 오는 데이터 양
                    .build()
                    .encode()
                    .toUri();
        
                try {
                    String jsonResponse = restTemplate.getForObject(uri, String.class);
        
                    Jobs dataResponse = objectMapper.readValue(jsonResponse, Jobs.class);
        
                    if (dataResponse.getJobsDetail() == null || dataResponse.getJobsDetail().getJobList()
                        .isEmpty()) {
                        log.error(GlobalErrorCode.NO_DATA_RECEIVED.getMessage());
                        throw new GlobalException(GlobalErrorCode.NO_DATA_RECEIVED);
                    }
        
                    return dataResponse;
        
                } catch (JsonProcessingException e) {
                    log.error("JSON 파싱 실패", e);
                    throw new GlobalException(GlobalErrorCode.JSON_PARSING_FAILED);
                }
        
            }
        
            /**
             * scheduler가 자정에 실행되기 때문에 전날 데이터를 가져오게 만든 메서드
             */
            private String getPublishedDate() {
                // 전날데이터
                LocalDate today = LocalDate.now().minusDays(1);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                return today.format(formatter);
            }
        
            /**
             * JobPosting, JobSkill 데이터들을 데이터베이스에 저장하기위한 메서드
             *
             * @param newJobs 가공된 JobPosting 데이터 리스트
             */
            private List<JobPosting> saveNewJobs(List<JobPosting> newJobs) {
                try {
                    List<JobPosting> savedJobPostingList = jobPostingRepository.saveAll(newJobs);
                    log.info("총 {}개의 공고를 저장했습니다.", savedJobPostingList.size());
                    return savedJobPostingList;
                } catch (Exception e) {
                    log.error(GlobalErrorCode.DATABASE_SAVE_FAILED.getMessage(), e);
                    throw new GlobalException(GlobalErrorCode.DATABASE_SAVE_FAILED);
                }
            }
        }
        ```

- Redis 캐싱 도입
    - Redis 캐시 도입 후 결과 (11초)

      ![성능 개선 후 (캐시 도입).png](attachment:cb78448f-ca84-463e-91b1-67786337a2f2:성능_개선_후_(캐시_도입).png)

    - Redis 캐싱 도입 후 코드
        
        ```java
        @Slf4j
        @Service
        @RequiredArgsConstructor
        public class SchedulerService {
        
        	private final JobSkillRepository jobSkillRepository;
        	private final JobPostingRepository jobPostingRepository;
        	private final ObjectMapper objectMapper;
        	private final RestTemplate restTemplate;
        	private final RetryTemplate retryTemplate;
        	private final RedisRepository redisRepository;
        
        	// URI로 조합할 OPEN API URL
        	private final String API_URL = "https://oapi.saramin.co.kr/job-search";
        
        	// URI로 조합할 apiKey
        	@Value("${api.key}")
        	private String apiKey;
        
        	// URI로 조합할 한 페이지당 가져올데이터 수
        	@Value("${api.count}")
        	private Integer count;
        
        	/**
        	 * 매일 자정(00:00)에 실행될 스케줄러 메서드입니다.
        	 * <p>
        	 * - retryTemplate.execute(context -> { ... }) -> API 요청이 실패할 경우 재시도를 수행하는 `RetryTemplate`을
        	 * 사용합니다. - processJobPostings (totalCount, totalJobs, pageNumber) -> API에서 채용 공고 데이터를 가져와
        	 * 데이터베이스에 저장하는 핵심 로직을 실행합니다.
        	 */
        	@Scheduled(cron = "0 0 0 * * ?", zone = "Asia/Seoul")
        	@Transactional
        	public void savePublicData() {
        		retryTemplate.execute(context -> {
        			int pageNumber = 0;
        			int totalCount = 0;
        //            int totalJobs = Integer.MAX_VALUE;
        			int totalJobs = 1000; //1. 1000개 기준 성능 측정
        //            int totalJobs = 10000; //2. 10000개 기준 성능 측정
        
        			LocalDateTime start = LocalDateTime.now();
        			processJobPostings(totalCount, totalJobs, pageNumber);
        			LocalDateTime end = LocalDateTime.now();
        
                    // 시간 차이 계산
        			Duration duration = Duration.between(start, end);
        
        			// 결과값 출력
        			log.info("작업 실행 시간: {} 밀리초", duration.toMillis());
        			log.info("작업 실행 시간: {} 초", duration.getSeconds());
        
                    return null;
        
                });
            }
        
        	/**
        	 * - 클래스 내에서 핵심로직이며, fetchJobPostings() 메소드를 통해 가져온 채용공고 데이터들을 저장하기위한 List<JobPosting>,
        	 * List<JobSkill> 로 변환하여, 저장하도록 하는 메서드이다. - 오늘 가져올수있는 총 공고수(totalJobs) 보다 데이텁베이스에 저장된
        	 * 공고수(totalCount) 크면 callBack 함수가 멈춘다.
        	 *
        	 * @param totalCount 현재 저장된 공고수
        	 * @param totalJobs  오늘 총 공고 수
        	 * @param pageNumber 현재 페이지 번호
        	 */
        	public void processJobPostings(int totalCount, int totalJobs, int pageNumber) {
        		Jobs jobs = fetchJobPostings(pageNumber, count);
        
        		// JobPosting 클래스로 담기
        		List<JobPosting> jobPostingList = jobs.getJobsDetail().getJobList().stream()
        			.map(Job::toEntity)
        			.toList();
        
        		//JSON 응답 파싱
        		List<Job> jobList = jobs.getJobsDetail().getJobList();
        		Map<Long, Job> jobMap = jobList.stream()
        			.collect(Collectors.toMap(job -> Long.parseLong(job.getId()), job -> job));
        
        		for (JobPosting jobPosting : jobPostingList) {
        			// JobId로 분류된 JobMap에서 Job 꺼내기
        			Job findJob = jobMap.get(jobPosting.getJobId());
        
        			//꺼내온 Job 안에 JobCode 꺼내기
        			String jobCode = findJob.getPositionDto().getJobCode().getCode();
        
        			//여러개면 , 기준으로 짜르기
        			String[] jobCodeArray = jobCode.split(",");
        
        			for (String s : jobCodeArray) {
        				String key = JobSkillConstant.JOB_SKILL_REDIS_KEY.getKey() + s;
        
        				//Redis에서 KEY값이 있는지 없는지 조회
        				//exists
        				boolean hasKeyResult = redisRepository.hasKey(key);
        
        				//만약 있다면 Redis에서 VALUE 조회해서 jobSkill 객체 생성
        				if (hasKeyResult) {
        					//JobSkillId 가져오는 로직
        					Long jobSkillId = Long.valueOf(redisRepository.get(key).toString());
        
        					//JobSkill 생성
        					JobSkill jobSkill = JobSkill.builder()
        						.id(jobSkillId)
        						.build();
        
        					jobPosting.getJobPostingJobSkillList().add(
        						JobPostingJobSkill.builder()
        							.jobPosting(jobPosting)
        							.jobSkill(jobSkill)
        							.build());
        				}
        			}
        		}
        
        		// 전체 저장
        		List<JobPosting> savedJobPostingList = saveNewJobs(jobPostingList);
        
        		//총 가져와야되는 개수 초기화
        		if (totalJobs == Integer.MAX_VALUE) {
        			totalJobs = Integer.parseInt(jobs.getJobsDetail().getTotal());
        		}
        
        		totalCount += savedJobPostingList.size();
        
        		if (totalCount < totalJobs) {
        			processJobPostings(totalCount, totalJobs, ++pageNumber);
        		}
        	}
        
        	/**
        	 * 지정된 페이지 번호와 가져올 데이터 개수를 기준으로 채용공고 데이터를 가져오는 메서드입니다.
        	 * <p>
        	 * - restTemplate : 주어진 URI로 채용공고 api 서버에 GET 요청을 보내, 응답 데이터를 받아오는 역할수행 - objectMapper : JSON
        	 * 문자열을 Jobs 객체로 변환하는 즉 역직렬화 역할수행.
        	 *
        	 * @param pageNumber 현재 페이지 번호
        	 * @param count      가져올 데이터 개수
        	 */
        	private Jobs fetchJobPostings(int pageNumber, int count) {
        
        		URI uri = UriComponentsBuilder.fromHttpUrl(API_URL)
        			.queryParam("access-key", apiKey)
        			// .queryParam("published", getPublishedDate())
        			.queryParam("job_mid_cd", "2")
        			.queryParam("start", pageNumber) // 현재 페이지숫자
        			.queryParam("count", count)
        			.queryParam("fields", "count")//한 번 호출시 가지고 오는 데이터 양
        			.build()
        			.encode()
        			.toUri();
        
        		try {
        			String jsonResponse = restTemplate.getForObject(uri, String.class);
        
        			Jobs dataResponse = objectMapper.readValue(jsonResponse, Jobs.class);
        
        			if (dataResponse.getJobsDetail() == null || dataResponse.getJobsDetail().getJobList()
        				.isEmpty()) {
        				log.error(GlobalErrorCode.NO_DATA_RECEIVED.getMessage());
        				throw new GlobalException(GlobalErrorCode.NO_DATA_RECEIVED);
        			}
        
        			return dataResponse;
        
        		} catch (JsonProcessingException e) {
        			log.error("JSON 파싱 실패", e);
        			throw new GlobalException(GlobalErrorCode.JSON_PARSING_FAILED);
        		}
        
        	}
        
        	/**
        	 * scheduler가 자정에 실행되기 때문에 전날 데이터를 가져오게 만든 메서드
        	 */
        	private String getPublishedDate() {
        		// 전날데이터
        		LocalDate today = LocalDate.now().minusDays(1);
        		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        		return today.format(formatter);
        	}
        
        	/**
        	 * JobPosting, JobSkill 데이터들을 데이터베이스에 저장하기위한 메서드
        	 *
        	 * @param newJobs 가공된 JobPosting 데이터 리스트
        	 */
        	private List<JobPosting> saveNewJobs(List<JobPosting> newJobs) {
        		try {
        			List<JobPosting> savedJobPostingList = jobPostingRepository.saveAll(newJobs);
        			log.info("총 {}개의 공고를 저장했습니다.", savedJobPostingList.size());
        			return savedJobPostingList;
        		} catch (Exception e) {
        			log.error(GlobalErrorCode.DATABASE_SAVE_FAILED.getMessage(), e);
        			throw new GlobalException(GlobalErrorCode.DATABASE_SAVE_FAILED);
        		}
        	}
        }
        ```

## 4. 결과 및 추가 조치

**총 개선 후 87% 성능 개선**

- 기존 코드 호출 결과 (87초)

![성능 개선 전](https://github.com/user-attachments/assets/cebe984e-560b-4d86-bfad-ecea4fb31d99)

- 저장 메소드 위치 수정 후 결과 (80초)

![저장 메소드 변경 후](https://github.com/user-attachments/assets/41749753-30d1-4f37-8dad-3b61e8997c8b)

- Redis 캐시 도입 후 결과 (11초)
  ![성능 개선 후 (캐시 도입)](https://github.com/user-attachments/assets/973a9e01-62fa-4974-88ce-7a60556a939c)

## 5. 회고 및 예방 조치

- 자주 조회가 일어나는 부분은 캐싱 도입을 고민해보자
    - 메모리를 사용하기 때문에 데이터 양과 데이터 정합성도 충분히 고려 후 적용 할 것
    - 현재의 상황은 JobSkill이 260개 밖에 없고 업데이트 될 일이 거의 없기에 도입하기 적합하다고 판단
- 메소드 위치에 따라 쿼리 발생하는 횟수가 늘어나는 경우가 생길수도 있으니 테스트하면서 쿼리 발생 양을 체크해볼 것

</div>
</details>

<details>
<summary>채팅 로직 성능 개선 - 장현석</summary>
<div markdown="1">

# 🛠 트러블슈팅 기록

## 1. 문제 요약

**발생 일시:** 2025-02-28

**증상:**

## 2. 원인 분석

- **기존 플로우**
    - 게시글 ID별 채팅방 기능 구현 (모집 완료된 게시글 기준)
    - 채팅 메시지를 Stomp(WebSocket) 기반으로 전송하여 MySQL에 저장
    - 채팅 내역을 MySQL에서 직접 조회해서 전체 메세지를 조회 필요
- **주요 원인**
    - MySQL이 실시간 메시지 저장 및 조회에 최적화되지 않음
    - STOMP를 통해 서버 간 WebSocket 세션을 유지해야 하는 부담 증가
    - 채팅 메시지 조회 시 MySQL에서 직접 조회하여 속도 저하

## 3. 해결 방법

- **기존 Stomp + MySQL 구조에서 Redis Pub/Sub + MongoDB로 변경**
    - **메시지 저장 방식 변경**: MySQL 대신 MongoDB에 저장
        - **MongoDB 인덱싱 활용**: 채팅 내역 조회 시 빠른 검색을 위한 인덱스 추가(postId + id)
    - **Redis Pub/Sub 도입**: STOMP에서 직접 메시지를 처리하는 방식에서 Redis를 통해 메시지를 Publish & Subscribe 하여 서버 간 세션 공유 문제 해결
    - **조회 성능 개선**: 최근 채팅 메시지는 Redis에서 캐싱하여 빠르게 조회
        - 만약 캐싱에서 과거 메세지들이 유실됐을 경우 캐시에서 가장 과거의 메세지를 기반으로 이전의 유실된 메세지를 mongo DB에서 조회후 캐싱에 추가후 조회
        - 채팅방별 활성도에 다라 캐싱 TTL 시간 동적으로 설정

## 4. 결과 및 추가 조치

**총 개선 후 성능 63% 향상**

- **기존 코드 호출 결과 (82ms)**
- **Redis 캐시 도입 후 결과 (30ms)**
    
    ![image.png](attachment:c6c650cf-8fa3-4fd2-8f96-8c009becb46b:image.png)
    
    ![image.png](attachment:d730344d-a13d-4b0d-947e-0c11263600ec:image.png)
    

## 5. 회고 및 예방 조치

- **실시간 처리가 필요한 경우 RDBMS보다 NoSQL을 적극 고려**
    - 채팅처럼 대량의 메시지를 빠르게 처리해야 하는 경우 MongoDB가 적합
    - MySQL을 사용할 경우 인덱스 튜닝 및 분산 처리 고려 필요
    - 추후 샤딩을 통해서 확장 가능
    - Capped Collection을 활용해서 과거 메세지 삭제 적용
- **자주 조회가 일어나는 데이터는 Redis 캐싱을 적극 활용**
    - 메모리를 사용하기 때문에 데이터 크기와 정합성을 충분히 고려한 후 적용
    - 현재의 상황에서는 채팅 메시지 중 최근 200개 메시지만 캐싱하는 방식이 적절하다고 판단
- **쿼리 발생 패턴 및 서버 부하 체크**
    - 메소드 위치 및 호출 로직 변경에 따라 불필요한 DB 쿼리 발생 여부 모니터링
    - Redis 및 MongoDB에 대한 성능 테스트를 주기적으로 실시하여 최적화 진행 예정

# 🛠 트러블슈팅 기록

## 1. 문제 요약

**발생 일시:** 2025-02-28

**증상:**

- 다수의 사용자가 동시 접속할 경우 WebSocket 메시지 처리 지연 발생
- 채팅 메시지 조회 시 속도 저하 및 서버 부하 증가

## 2. 원인 분석

- **기존 플로우**
    - 게시글 ID별 채팅방 기능 구현 (모집 완료된 게시글 기준)
    - 채팅 메시지를 STOMP(WebSocket) 기반으로 전송하여 MySQL에 저장
    - 채팅 내역을 MySQL에서 직접 조회하여 전체 메시지를 조회
- **주요 원인**
    - STOMP를 통해 서버 간 WebSocket 세션을 유지해야 하는 부담 증가
    - 다량의 메시지 조회 시 MySQL에서 직접 조회하여 속도 저하
    - 다중 사용자 접속 시 동시 요청 처리로 인해 데이터베이스 부하 증가

## 3. 해결 방법

- **기존 Stomp + MySQL 구조에서 Redis Pub/Sub + MongoDB로 변경**
    - **메시지 저장 방식 변경**: MySQL 대신 MongoDB에 저장하여 조회 속도 개선
        - **MongoDB 인덱싱 활용**: 채팅 내역 조회 시 빠른 검색을 위한 인덱스 추가 (`postId + id`)
    - **Redis Pub/Sub 도입**: STOMP에서 직접 메시지를 처리하는 방식에서 Redis를 통해 메시지를 Publish & Subscribe 하여 서버 간 세션 공유 문제 해결
    - **조회 성능 개선**:
        - 최근 채팅 메시지는 Redis에서 캐싱하여 빠르게 조회
        - 캐싱에서 과거 메시지가 유실된 경우 **캐시의 가장 오래된 메시지를 기준으로 MongoDB에서 이전 데이터를 조회 후 캐싱에 추가한 뒤 조회 수행**
        - 채팅방별 활성도에 따라 **캐싱 TTL 시간 동적 조정**

## 4. 결과 및 추가 조치

**총 개선 후 성능 63% 향상**

- **기존 코드 호출 결과 (82ms)**
    
    ![image.png](attachment:c6c650cf-8fa3-4fd2-8f96-8c009becb46b:image.png)
    
- **Redis 캐시 도입 후 결과 (30ms)**
    
    ![image.png](attachment:d730344d-a13d-4b0d-947e-0c11263600ec:image.png)
    

## 5. 회고 및 예방 조치

- **실시간 처리가 필요한 경우 RDBMS보다 NoSQL을 적극 고려**
    - 채팅처럼 대량의 메시지를 빠르게 처리해야 하는 경우 MongoDB가 적합
    - MySQL을 사용할 경우 **인덱스 튜닝 및 분산 처리 고려 필요**
    - **추후 샤딩 적용을 통해 확장 가능**
    - **Capped Collection을 활용하여 일정 메시지 개수 이상이 되면 자동 삭제 적용**
- **자주 조회가 일어나는 데이터는 Redis 캐싱을 적극 활용**
    - **메모리를 효율적으로 사용하기 위해 캐싱 데이터 크기 및 정합성 검토 후 적용**
    - 현재의 상황에서는 채팅 메시지 중 **최근 200개 메시지만 캐싱하는 방식이 적절**하다고 판단
    - **Redis의 Expire를 통해 비활성 채팅방의 캐싱 데이터(1일 동안 비활성화)를 자동 제거**
- **쿼리 발생 패턴 및 서버 부하 체크**
    - **메소드 위치 및 호출 로직 변경에 따라 불필요한 DB 쿼리 발생 여부 모니터링**
    - **대량 메시지 처리 시 부하 발생 여부를 점검하여 필요 시 Redis Streams 도입 고려**

</div>
</details>
