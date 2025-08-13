package com.example.datn_team_ae.Service;

import com.example.datn_team_ae.DTO.SanPhamDTO;
import com.example.datn_team_ae.DTO.SanPhamViewDto;
import com.example.datn_team_ae.Entity.NhanVien;
import com.example.datn_team_ae.Entity.SanPham;
import com.example.datn_team_ae.Repository.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import jakarta.transaction.Transactional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.*;


@Service
public class SanPhamService {

    @Autowired
    private SanPhamRepository sanPhamRepository;

    @Autowired
    private DanhMucRepository danhMucRepository;

    @Autowired
    private ThuongHieuRepository thuongHieuRepository;

    @Autowired
    private ChatLieuRepository chatLieuRepository;

    @Autowired
    private XuatXuRepository xuatXuRepository;

    @Autowired
    private SanPhamChiTietRepository sanPhamChiTietRepository;

    // Tạo mã sản phẩm ngẫu nhiên: SP + 6 số
    private String generateMaSanPham() {
        return "SP" + (100000 + new Random().nextInt(900000));
    }

    @Transactional
    public synchronized ResponseEntity<?> addSanPham(SanPhamDTO sanPhamDTO, HttpSession session) {
        try {
            // Check tên sản phẩm không được để trống hoặc chỉ toàn khoảng trắng
            if (sanPhamDTO.getTenSanPham() == null || sanPhamDTO.getTenSanPham().trim().isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("message", "Tên sản phẩm không được để trống!"));
            }

            // Check tên sản phẩm không được để trống hoặc chỉ toàn khoảng trắng
            if (sanPhamDTO.getMoTa() == null || sanPhamDTO.getMoTa().trim().isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("message", "Mô tả không được để trống!"));
            }

            // Check tên sản phẩm không được để trống hoặc chỉ toàn khoảng trắng
            if (sanPhamDTO.getThuongHieuId() == null) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("message", "Thương hiệu không được để trống!"));
            }

            // Check tên sản phẩm không được để trống hoặc chỉ toàn khoảng trắng
            if (sanPhamDTO.getXuatXuId() == null) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("message", "Xuất xứ không được để trống!"));
            }

            // Check tên sản phẩm không được để trống hoặc chỉ toàn khoảng trắng
            if (sanPhamDTO.getChatLieuId() == null) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("message", "Chất liệu không được để trống!"));
            }

            // Lấy nhân viên từ session
            NhanVien nhanVienSession = (NhanVien) session.getAttribute("userNhanVien");
            if (nhanVienSession == null) {
                throw new Exception("Bạn cần đăng nhập.");
            }

            // Tạo sản phẩm
            SanPham sanPham = new SanPham();
            sanPham.setMaSanPham(generateMaSanPham());
            sanPham.setTenSanPham(sanPhamDTO.getTenSanPham().trim()); // luôn trim tên
            sanPham.setSoLuong(sanPhamDTO.getSoLuong());
            sanPham.setMoTa(sanPhamDTO.getMoTa());
            sanPham.setTrangThai("Ngừng Hoạt Động");
            sanPham.setNgayTao(LocalDate.now());
            sanPham.setNgaySua(LocalDate.now());
            sanPham.setNguoiTao(nhanVienSession.getHoTen());
            sanPham.setNguoiSua(nhanVienSession.getHoTen());
            sanPham.setDeleted(false);

            // Set các liên kết
            sanPham.setThuongHieu(thuongHieuRepository.findById(sanPhamDTO.getThuongHieuId()).orElseThrow(
                    () -> new RuntimeException("Thương hiệu không tồn tại!")));
            sanPham.setDanhMuc(danhMucRepository.findById(sanPhamDTO.getDanhMucId()).orElseThrow(
                    () -> new RuntimeException("Danh mục không tồn tại!")));
            sanPham.setChatLieu(chatLieuRepository.findById(sanPhamDTO.getChatLieuId()).orElseThrow(
                    () -> new RuntimeException("Chất liệu không tồn tại!")));
            sanPham.setXuatXu(xuatXuRepository.findById(sanPhamDTO.getXuatXuId()).orElseThrow(
                    () -> new RuntimeException("Xuất xứ không tồn tại!")));

            if ( sanPhamRepository.existsByTenSanPhamAndThuongHieu_IdAndXuatXu_IdAndDanhMuc_IdAndChatLieu_Id(
                    sanPhamDTO.getTenSanPham(), sanPhamDTO.getThuongHieuId(), sanPhamDTO.getXuatXuId(), sanPhamDTO.getDanhMucId(), sanPhamDTO.getChatLieuId())) {
                return ResponseEntity
                        .status(HttpStatus.CONFLICT) // 409 Conflict
                        .body(Map.of("message", "Tên sản phẩm đã tồn tại!"));
            }

            // Lưu vào DB
            SanPham savedSanPham = sanPhamRepository.save(sanPham);

            return ResponseEntity.ok(Map.of(
                    "message", "Thêm sản phẩm thành công!",
                    "id", savedSanPham.getId()
            ));

        } catch (DataIntegrityViolationException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Lỗi dữ liệu! Vui lòng kiểm tra lại thông tin sản phẩm."));
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Đã xảy ra lỗi trong quá trình thêm sản phẩm."));
        }
    }

    @Transactional
    public synchronized SanPham updateSanPham(Integer id, SanPhamDTO sanPhamDTO) {
        try {
            SanPham sanPham = sanPhamRepository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sản phẩm không tồn tại!"));


            // Cập nhật thông tin sản phẩm
            sanPham.setTenSanPham(sanPhamDTO.getTenSanPham());
            sanPham.setMoTa(sanPhamDTO.getMoTa());

            if (sanPhamDTO.getSoLuong() != null) {
                sanPham.setSoLuong(sanPhamDTO.getSoLuong());
                sanPham.setTrangThai(sanPhamDTO.getSoLuong() > 0 ? "Đang Hoạt Động" : "Ngừng Hoạt Động");
            }

            sanPham.setNgaySua(LocalDate.now());
            sanPham.setNguoiSua(sanPhamDTO.getNguoiSua());

            // Cập nhật quan hệ
            sanPham.setThuongHieu(thuongHieuRepository.findById(sanPhamDTO.getThuongHieuId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Thương hiệu không hợp lệ!")));
            sanPham.setDanhMuc(danhMucRepository.findById(sanPhamDTO.getDanhMucId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Danh mục không hợp lệ!")));
            sanPham.setChatLieu(chatLieuRepository.findById(sanPhamDTO.getChatLieuId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Chất liệu không hợp lệ!")));
            sanPham.setXuatXu(xuatXuRepository.findById(sanPhamDTO.getXuatXuId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Xuất xứ không hợp lệ!")));

            if (sanPhamRepository.existsByTenSanPhamAndThuongHieu_IdAndXuatXu_IdAndDanhMuc_IdAndChatLieu_IdAndIdNot(
                    sanPhamDTO.getTenSanPham(),
                    sanPhamDTO.getThuongHieuId(),
                    sanPhamDTO.getXuatXuId(),
                    sanPhamDTO.getDanhMucId(),
                    sanPhamDTO.getChatLieuId(),
                    id)) {

                throw new ResponseStatusException(HttpStatus.CONFLICT, "Sản phẩm đã tồn tại trong danh mục và thương hiệu này!");
            }

            return sanPhamRepository.save(sanPham);

        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Lỗi dữ liệu! Vui lòng kiểm tra lại thông tin sản phẩm.", e);
        } catch (ResponseStatusException e) {
            throw e; // Giữ nguyên lỗi có sẵn
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Đã xảy ra lỗi trong quá trình cập nhật sản phẩm.", e);
        }
    }


    public boolean toggleSanPhamStatus(Integer id) {
        Optional<SanPham> optionalSanPham = sanPhamRepository.findById(id);
        if (optionalSanPham.isPresent()) {
            SanPham sanPham = optionalSanPham.get();

            // Thay đổi trạng thái và đánh dấu xóa mềm
            if ("Đang Hoạt Động".equals(sanPham.getTrangThai())) {
                sanPham.setTrangThai("Ngừng Hoạt Động");
                sanPham.setDeleted(true);
            } else {
                sanPham.setTrangThai("Đang Hoạt Động");
                sanPham.setDeleted(false);
            }

            sanPhamRepository.save(sanPham);
            return true;
        }
        return false;
    }

    public Page<SanPhamViewDto> getSanPhams(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return sanPhamChiTietRepository.findSanPhamChiTiet(pageable);
    }


    public Page<SanPhamViewDto> searchSanPhams(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return sanPhamChiTietRepository.searchSanPham(keyword, pageable);
    }
    public Page<SanPhamViewDto> filterSanPhams(String danhMuc, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return sanPhamChiTietRepository.filterSanPham(danhMuc, pageable);
    }


}
