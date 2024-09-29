/**
 * 최초 생성일: 2024-09-29
 * @author 임성현
 * 
 * 최종 수정일: 2024-09-29
 * @author 임성현
 * 
 * 주요 수정 내용: 파일 다운로드를 위한 액션 클래스 생성
 */
package action;

import java.io.*;
import java.net.URLEncoder;
import javax.servlet.http.*;
import vo.ActionForward;

public class ApprovalContentsFileAction implements Action {
	
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		 
		 try {
	            // fileName 파라미터로 파일명을 가져온다.
	            String fileName = request.getParameter("filename");

	            // 파일이 실제 업로드 되어있는(파일이 존재하는) 경로를 지정한다._경로수정
	            String filePath = "C:\\semiproject\\BeansPaM\\src\\main\\webapp\\BeansPaMUploads\\";

	            // 경로와 파일명으로 파일 객체를 생성한다.
	            File dFile = new File(filePath, fileName);

	            // 파일 길이를 가져온다.
	            int fSize = (int) dFile.length();

	            // 파일이 존재
	            if (fSize > 0) {

	                // 파일명을 URLEncoder 하여 attachment, Content-Disposition Header로 설정
	                String encodedFilename = "attachment; filename*=" + "UTF-8" + "''" + URLEncoder.encode(fileName, "UTF-8");

	                // ContentType 설정 (8비트로 된 데이터라는 의미임. 1바이트 단위의 데이터들을 핸들링하는 타입을 의미함.)
	                response.setContentType("application/octet-stream; charset=utf-8");

	                // Header 설정
	                response.setHeader("Content-Disposition", encodedFilename);

	                // ContentLength 설정
	                response.setContentLengthLong(fSize);

	                BufferedInputStream in = null;
	                BufferedOutputStream out = null;

	                in = new BufferedInputStream(new FileInputStream(dFile));
	                out = new BufferedOutputStream(response.getOutputStream());

	                try {
	                    byte[] buffer = new byte[4096];
	                    int bytesRead = 0;

	                    /*
	                    모두 현재 파일 포인터 위치를 기준으로 함 (파일 포인터 앞의 내용은 없는 것처럼 작동)
	                    int read() : 1byte씩 내용을 읽어 정수로 반환
	                    int read(byte[] b) : 파일 내용을 한번에 모두 읽어서 배열에 저장
	                    int read(byte[] b. int off, int len) : 'len'길이만큼만 읽어서 배열의 'off'번째 위치부터 저장
	                    */
	                    while ((bytesRead = in .read(buffer)) != -1) {
	                        out.write(buffer, 0, bytesRead);
	                    }

	                    // 버퍼에 남은 내용이 있다면, 모두 파일에 출력
	                    out.flush();
	                } finally {
	                    /*
	                    현재 열려 in,out 스트림을 닫음
	                    메모리 누수를 방지하고 다른 곳에서 리소스 사용이 가능하게 만듬
	                    */
	                    in.close();
	                    out.close();
	                }
	            } else {
	                throw new FileNotFoundException("파일이 없습니다.");
	            }
	        } catch (Exception e) {
	            e.getMessage();
	        } 
	        	return null;    	
	}
}
