
# Deploy bằng gcloud
gcloud run deploy booking-resort-api `
  --image dauxu/mobile-api:1.0.3 `
  --platform managed `
  --region asia-southeast1 `
  --allow-unauthenticated `
  --port 8080 `


# ============================
# XÓA REVISION CŨ (giữ lại 3 mới nhất)
# ============================

Write-Host "`nCleaning up old revisions for service: doanjava-api"

# Lấy danh sách tất cả revision (mới đến cũ), bỏ 3 cái đầu
$oldRevisions = gcloud run revisions list `
  --service=booking-resort-api `
  --region=asia-southeast1 `
  --sort-by="~metadata.creationTimestamp" `
  --format="value(metadata.name)" | Select-Object -Skip 3

# Xóa các revision cũ
foreach ($rev in $oldRevisions) {
  Write-Host "Deleting revision: $rev"
  gcloud run revisions delete $rev --region=asia-southeast1 --quiet
}

Write-Host "`nDeployment and cleanup done."